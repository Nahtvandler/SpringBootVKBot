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

    Gson gson = new Gson();

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @RequestMapping("/botCall")
    public String request(@RequestBody String request) {
        logger.info("Callback request: " + request);

        JsonObject jsonObject = (JsonObject) gson.fromJson(request, JsonObject.class);

        String type = jsonObject.get("type").getAsString();
        if (type.equalsIgnoreCase("confirmation")) {
            return "b8bfb568";
        }

        CallbackApiHandler handler = new CallbackApiHandler();
        handler.parse(jsonObject);

        return HttpStatus.OK.toString();
    }
}