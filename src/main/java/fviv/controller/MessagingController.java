package fviv.controller;

import fviv.messaging.Message;
import fviv.messaging.PostOffice;
import fviv.messaging.SendMessageForm;
import fviv.util.time.JavaScriptDateTimeFormatters;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by justusadam on 09/12/14.
 */
@RestController
public class MessagingController {

    private static final String IS_AJAX_HEADER = "X-Requested-With=XMLHttpRequest";

    private PostOffice postOffice;

    @Deprecated
    public UserAccount testUser;

    @Autowired
    public MessagingController(PostOffice postOffice, UserAccountManager userManager) {
        this.postOffice = postOffice;
        this.testUser = userManager.create("bob", "passwd", PostOffice.receiverRole, PostOffice.senderRole);
        testUser.setFirstname("Bob");
        testUser.setLastname("Barker");
        userManager.save(testUser);
    }

    @RequestMapping(value = "/messaging/send", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
    public String send(@LoggedIn Optional<UserAccount> user, @ModelAttribute("sendMessageForm") @Validated SendMessageForm messageForm, BindingResult bindingResult) {
        System.out.println("wrong method");
        if (bindingResult.hasErrors()) return "error";
        if (!user.isPresent()) return "error";

        return postOffice.sendMessage(user.get(), messageForm.getRecipient(), messageForm.getMessage()) ? "success" : "failed";
    }

    @Deprecated
    @RequestMapping(value = "/messaging/test/send", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
    public Object send(@ModelAttribute("sendMessageForm") @Validated SendMessageForm messageForm, BindingResult bindingResult) {
        System.out.println("got message " + messageForm.getMessage() );

        postOffice.sendMessage(testUser, testUser, messageForm.getMessage());
        return true;
    }


    @RequestMapping(value = "/messaging/get", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
    public List<Message> getMessages(@LoggedIn Optional<UserAccount> user, @RequestParam("last") String date){
        if (!user.isPresent()) return new LinkedList<>();
        ZonedDateTime dateTime = ZonedDateTime.parse(date, JavaScriptDateTimeFormatters.javaScriptUTCDateTimeFormat);
        System.out.println("messages requested " + date);

        return postOffice.getMessages(user.get(), dateTime);
    }

    @Deprecated
    @RequestMapping(value = "/messaging/test/get", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
    public List<Message> getTestMessages(@RequestParam("last") String date){
        ZonedDateTime dateTime = ZonedDateTime.parse(date, JavaScriptDateTimeFormatters.javaScriptUTCDateTimeFormat);
        System.out.println("messages requested " + date);

        return postOffice.getMessages(testUser, dateTime);
    }

    @Deprecated
    @RequestMapping(value = "/messaging/test/get/receivers", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
    public List<UserAccount> getTestReceivers() {
        return postOffice.getRecipients(testUser);
    }

    @RequestMapping(value = "/messaging/get/receivers", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
    public List<UserAccount> getReceivers(@LoggedIn Optional<UserAccount> user) {
        if (!user.isPresent()) return new LinkedList<>();
        return postOffice.getRecipients(user.get());
    }
}
