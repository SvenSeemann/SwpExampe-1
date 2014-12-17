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
 * @author justusadam
 * @version 0.2
 */
@RestController
public class MessagingController {

    /**
     * Header marking an incoming request as being sent by ajax
     */
    private static final String IS_AJAX_HEADER = "X-Requested-With=XMLHttpRequest";

    /**
     * Internal handler object for all transactions concerning messages.
     */
    private PostOffice postOffice;

    /**
     * User designed for testing.
     */
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

    /**
     * Receiving method for a 'send message' request
     *
     * @param user The user currently logged in
     * @param messageForm Object representing the HTML form sent in with the request
     * @param bindingResult result of trying to bind the incoming form to the MessageForm object
     * @return boolean indicating whether the message was successfully sent
     */
    @RequestMapping(value = "/messaging/send", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
    public boolean send(@LoggedIn Optional<UserAccount> user, @ModelAttribute("sendMessageForm") @Validated SendMessageForm messageForm, BindingResult bindingResult) {
//        System.out.println("wrong method");
        return !bindingResult.hasErrors() && user.isPresent() && postOffice.sendMessage(user.get(), messageForm.getRecipient(), messageForm.getMessage());

    }

    @Deprecated
    @RequestMapping(value = "/messaging/test/send", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
    public Object send(@ModelAttribute("sendMessageForm") @Validated SendMessageForm messageForm, BindingResult bindingResult) {
        System.out.println("got message " + messageForm.getMessage() );

        postOffice.sendMessage(testUser, messageForm.getRecipient(), messageForm.getMessage());
        return true;
    }

    /**
     * Request all message the user has received after the date
     *
     * @param user The currently logged in user issuing the request
     * @param date The earliest date from which to get the messages
     * @return List of Messages, empty if user is unauthorized
     */
    @RequestMapping(value = "/messaging/get", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
    public List<Message> getMessages(@LoggedIn Optional<UserAccount> user, @RequestParam("last") String date){
        return user.isPresent() ?
                postOffice.getMessages(
                        user.get(), ZonedDateTime.parse(date, JavaScriptDateTimeFormatters.javaScriptUTCDateTimeFormat)
                ) :
                new LinkedList<>();
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

    /**
     * Requesting all possible recipients
     *
     * @param user logged in user issuing the request
     * @return List of Recipients, empty if user is unauthorized
     */
    @RequestMapping(value = "/messaging/get/receivers", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
    public List<UserAccount> getReceivers(@LoggedIn Optional<UserAccount> user) {
        return user.isPresent() ? postOffice.getRecipients(user.get()) : new LinkedList<>();
    }
}
