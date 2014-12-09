package fviv.controller;

import fviv.messaging.PostOffice;
import fviv.messaging.SendMessageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by justusadam on 09/12/14.
 */
@org.springframework.stereotype.Controller
public class Controller {

    private static final String IS_AJAX_HEADER = "X-Requested-With=XMLHttpRequest";

    private PostOffice postOffice;

    @Autowired
    public Controller(PostOffice postOffice) {
        this.postOffice = postOffice;
    }

    @RequestMapping(value = "/messaging/send", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
    public String send(@ModelAttribute("sendMessageForm") @Validated SendMessageForm messageForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "error";

        return postOffice.sendMessage(messageForm.getSender(), messageForm.getReceiver(), messageForm.getMessage()) ? "sucess" : "failed";
    }

    @RequestMapping(value = "/")
    public String handle(Model model) {
        return "index";
    }


}
