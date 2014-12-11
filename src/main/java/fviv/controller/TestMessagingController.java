package fviv.controller;

import fviv.messaging.PostOffice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by justusadam on 11/12/14.
 */
@Controller
public class TestMessagingController {

    private PostOffice postOffice;

    @Autowired
    public TestMessagingController(PostOffice postOffice) {
        this.postOffice = postOffice;
    }

    @RequestMapping(value = "/messaging/test")
    public String testhandle(Model model) {
        model.addAttribute("recipients", postOffice.getRecipients());
        return "testmessaging";
    }
}
