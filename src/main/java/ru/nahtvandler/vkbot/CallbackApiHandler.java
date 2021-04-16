package ru.nahtvandler.vkbot;

import com.vk.api.sdk.callback.CallbackApi;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.wall.Wallpost;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CallbackApiHandler extends CallbackApi {
    private final static Logger logger = LogManager.getLogger(CallbackApiHandler.class);

    private final static String GAME_TAG = "#VGGame";

    @Override
    public void wallPostNew(Integer groupId, Wallpost message) {
        super.wallPostNew(groupId, message);

        if (StringUtils.isBlank(message.getText()) || !message.getText().contains(GAME_TAG)) {
            return;
        }

        try {
            VKManager.getInstance().resendWallPost(message);
        } catch (ClientException | ApiException e) {
            logger.error(e);
        }
    }

    @Override
    public void messageNew(Integer groupId, Message message) {
        super.messageNew(groupId, message);
    }

    @Override
    public void confirmation(Integer groupId, String secret) {
        super.confirmation(groupId, secret);
    }
}
