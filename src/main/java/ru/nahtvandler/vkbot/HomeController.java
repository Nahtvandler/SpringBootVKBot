package ru.nahtvandler.vkbot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/vkBot")
public class HomeController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @RequestMapping("/botCall")
    public String request(@RequestBody String request) {
        CallbackApiHandler handler = new CallbackApiHandler();
        handler.parse(request);

        return HttpStatus.OK.toString();
    }
}