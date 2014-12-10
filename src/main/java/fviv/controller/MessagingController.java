package fviv.controller;

import fviv.messaging.PostOffice;
import fviv.messaging.SendMessageForm;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

/**
 * Created by justusadam on 09/12/14.
 */
@org.springframework.stereotype.Controller
public class MessagingController {

    private static final String IS_AJAX_HEADER = "X-Requested-With=XMLHttpRequest";

    private PostOffice postOffice;

    @Autowired
    public MessagingController(PostOffice postOffice) {
        this.postOffice = postOffice;
    }

    @RequestMapping(value = "/messaging/send", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
    public String send(@LoggedIn Optional<UserAccount> user, @ModelAttribute("sendMessageForm") @Validated SendMessageForm messageForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "error";
        if (!user.isPresent()) return "error";

        return postOffice.sendMessage(user.get(), messageForm.getRecipient(), messageForm.getMessage()) ? "success" : "failed";
    }

    @RequestMapping(value = "/messaging/test/send", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
    public String send(@LoggedIn Optional<UserAccount> user) {

        if (!user.isPresent()) return "error";

        return "hello";
    }

    @RequestMapping(value = "/")
    public String handle(Model model) {
        return "index";
    }

    public void withMessaging(Model model, Optional<UserAccount> user) {
        if (user.isPresent()) {
            model.addAttribute("recipients", postOffice.getRecipients());
            model.addAttribute("messages", postOffice.getMessages(user.get()));
        }
    }

    @RequestMapping(value = "/messaging/test")
    public String testhandle(Model model) {
        model.addAttribute("recipients", postOffice.getRecipients());
        return "testmessaging";
    }

    @RequestMapping(value = "/messaging/get", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
    public String getMessages(){
        return "";
    }
}
