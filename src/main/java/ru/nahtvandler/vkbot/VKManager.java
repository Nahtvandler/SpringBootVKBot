package ru.nahtvandler.vkbot;

import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetMembersResponse;
import com.vk.api.sdk.objects.wall.Wallpost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class VKManager {
    private final static Logger logger = LogManager.getLogger(VKManager.class);

    private static VKManager INSTANCE;
    VKCore vkCore;

    public VKManager() throws ClientException, ApiException {
        this.vkCore = new VKCore();
    }

    public static synchronized VKManager getInstance() throws ClientException, ApiException {
        if (INSTANCE == null) {
            INSTANCE = new VKManager();
        }

        return INSTANCE;
    }

    public List<Integer> getGroupMembers() {
        GroupActor actor = vkCore.getActor();

        try {
            GetMembersResponse response = vkCore.getVk().groups().getMembers(actor)
                    .groupId(actor.getGroupId().toString()).execute();

            return response.getItems();
        } catch (ApiException | ClientException e) {
            logger.error(e);
            return new ArrayList<Integer>();
        }
    }

    public void resendWallPost(Wallpost wallpost) throws ClientException, ApiException {
        String attachment = MessageFormat.format("wall{o}_{1}", wallpost.getId(), wallpost.getOwnerId());

        vkCore.getVk().messages().send(vkCore.getActor())
                .peerIds(getGroupMembers()).randomId(0).attachment(attachment).message("Глянь пост").execute();
    }
}