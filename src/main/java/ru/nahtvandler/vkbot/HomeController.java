package ru.nahtvandler.vkbot;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.lang3.StringUtils;
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
    CircularFifoQueue<String> eventQueue = new CircularFifoQueue<>(10);

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

        String event = jsonObject.get("event_id").getAsString();
        if (!doRequestCheck(event)) {
            return "ok";
        }

        String type = jsonObject.get("type").getAsString();
        if (type.equalsIgnoreCase("confirmation")) {
            return "5009f97a";
        }
        handler.parse(jsonObject);

        return "ok";
    }

    private boolean doRequestCheck(String event) {
        if (StringUtils.isBlank(event)) {
            return false;
        }

//        if (jsonObject.get("secret").getAsString().equals("")) {
//            return "ok";
//        }

        if (eventQueue.contains(event)) {
            return false;
        } else {
            eventQueue.offer(event);
            return true;
        }
    }

    @RequestMapping("/check")
    public String check() {
        return HttpStatus.OK.toString();
    }
}