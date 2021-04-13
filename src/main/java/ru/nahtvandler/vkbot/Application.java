package ru.nahtvandler.vkbot;

import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetMembersResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);

//        VKCore vkCore = null;
//        try {
//            vkCore = new VKCore();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        GroupActor actor = vkCore.getActor();
//
//        try {
//            GetMembersResponse response = vkCore.getVk().groups().getMembers(actor)
//                    .groupId(actor.getGroupId().toString())
//                    .execute();
//
//            VKCore finalVkCore = vkCore;
//            response.getItems().forEach(id -> {
//                try {
//                    finalVkCore.getVk().messages().send(finalVkCore.getActor()).peerId(id).randomId(0).message("Тестовое сообщение от бота").execute();
//                } catch (ApiException e) {
//                    e.printStackTrace();
//                } catch (ClientException e) {
//                    e.printStackTrace();
//                }
//            });
//
//
//
//
//        } catch (ApiException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//
//
//        vkCore.getVk().groups().getMembers(vkCore.getActor()).build();

    }
}