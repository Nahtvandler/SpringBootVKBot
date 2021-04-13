package ru.nahtvandler.vkbot;

import com.vk.api.sdk.callback.CallbackApi;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.wall.Wallpost;

public class CallbackApiHandler extends CallbackApi {
    @Override
    public void wallPostNew(Integer groupId, Wallpost message) {
        super.wallPostNew(groupId, message);

        System.out.println(message);

    }

    @Override
    public void messageNew(Integer groupId, Message message) {
        super.messageNew(groupId, message);
    }
}
