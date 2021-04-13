package ru.nahtvandler.vkbot;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/vkBot")
public class HomeController {
    private final static Logger logger = LogManager.getLogger(HomeController.class);

    CallbackApiHandler handler = new CallbackApiHandler();
    Gson gson = new Gson();

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @RequestMapping("/botCall")
    public String request(@RequestBody String request) {
        logger.info("Callback request: " + request);

        JsonObject jsonObject = (JsonObject) gson.fromJson(request, JsonObject.class);

//        if (jsonObject.get("secret").getAsString().equals("")) {
//            return "ok";
//        }

        String type = jsonObject.get("type").getAsString();
        if (type.equalsIgnoreCase("confirmation")) {
            return "9f1bfa3a";
        }

        handler.parse(jsonObject);

        return "ok";
    }

    @RequestMapping("/check")
    public String check() {
        return HttpStatus.OK.toString();
    }
}