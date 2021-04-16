package ru.nahtvandler.vkbot;

import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetCallbackConfirmationCodeResponse;
import com.vk.api.sdk.objects.groups.responses.GetMembersResponse;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.objects.wall.Wallpost;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String attachment = MessageFormat.format("wall{0}_{1}", wallpost.getOwnerId().toString(), wallpost.getId().toString());

//        vkCore.getVk().messages().send(vkCore.getActor())
//                .peerIds(getGroupMembers().get(0)).randomId(new Random().nextInt())
//                .attachment(attachment).message("пост" + System.currentTimeMillis()).keyboard(createKeyborad(wallpost)).execute();

        try {
            vkCore.getVk().messages().send(vkCore.getActor())
                    .peerIds(getGroupMembers()).randomId(new Random().nextInt())
                    .attachment(attachment).message("пост" + System.currentTimeMillis()).keyboard(createKeyborad(wallpost)).execute();

        } catch (ClientException e) {
            //do nothing
        }

//        try {
//            vkCore.getVk().messages().send(vkCore.getActor())
//                    .peerIds(getGroupMembers()).randomId(new Random().nextInt())
//                    .attachment(attachment).message("пост" + System.currentTimeMillis()).execute();
//        } catch (ClientException e) {
//            //do nothing
//        }
    }

    private Keyboard createKeyborad(Wallpost wallpost) {
        String text = wallpost.getText();

        //Pattern pattern = Pattern.compile(".*(#coordinates)([0-9.,]+).*");
        Pattern pattern = Pattern.compile(".*(Координаты:)([\\s0-9.,]+).*");
        Matcher matcher = pattern.matcher(text);

        String latitude = null;
        String longitude = null;
        if (matcher.find()) {
            String[] arr = matcher.group(2).split(",");
            latitude = arr[0].trim();
            longitude = arr[1].trim();
        }

        if (StringUtils.isBlank(latitude) && StringUtils.isBlank(longitude)) {
            return new Keyboard();
        }

        Keyboard keyboard = new Keyboard();
        keyboard.setInline(true);

        KeyboardButtonAction actionLink = new KeyboardButtonAction();
        actionLink.setType(TemplateActionTypeNames.OPEN_LINK);
        actionLink.setLink(MessageFormat.format("https://yandex.ru/maps/?pt={0},{1}&z=16&l=map", longitude, latitude));
        actionLink.setLabel("Полигон на Яндекс.Картах");

        KeyboardButton buttonLink = new KeyboardButton();
        buttonLink.setAction(actionLink);

        List<KeyboardButton> firstLine = new ArrayList<>();
        firstLine.add(buttonLink);


        List<List<KeyboardButton>> buttons = new ArrayList<>();
        buttons.add(firstLine);

        keyboard.setButtons(buttons);

        return keyboard;
    }

//    private Keyboard createKeyborad() {
//        Keyboard keyboard = new Keyboard();
//        keyboard.setInline(true);
//
//
//        KeyboardButtonAction actionText = new KeyboardButtonAction();
//        actionText.setType(TemplateActionTypeNames.TEXT);
//        actionText.setPayload("{\"button\": \"2\"}");
//        actionText.setLabel("blue");
//
//        KeyboardButton buttonText = new KeyboardButton();
//        buttonText.setAction(actionText);
//        buttonText.setColor(KeyboardButtonColor.POSITIVE);
//
//        KeyboardButtonAction actionLocation = new KeyboardButtonAction();
//        actionLocation.setType(TemplateActionTypeNames.LOCATION);
//        actionLocation.setPayload("{\"button\": \"2\"}");
//
//        KeyboardButton buttonLocation = new KeyboardButton();
//        buttonLocation.setAction(actionLocation);
//
//        KeyboardButtonAction actionLink = new KeyboardButtonAction();
//        actionLink.setType(TemplateActionTypeNames.OPEN_LINK);
//        actionLink.setLink("https://yandex.ru/maps/?pt=37.370716,55.819574&z=16&l=map");
//        actionLink.setLabel("Полигон на Яндекс.Картах");
//
//        KeyboardButton buttonLink = new KeyboardButton();
//        buttonLink.setAction(actionLink);
//
//        List<KeyboardButton> firstLine = new ArrayList<>();
//        firstLine.add(buttonLocation);
//
//        List<KeyboardButton> secondLine = new ArrayList<>();
//        secondLine.add(buttonText);
//        secondLine.add(buttonLink);
//
//
//        List<List<KeyboardButton>> buttons = new ArrayList<>();
//        buttons.add(firstLine);
//        buttons.add(secondLine);
//
//        keyboard.setButtons(buttons);
//
//        return keyboard;
//    }

    /**
     * Возвращает ключ подтверждения группы. Ключ может использоваться
     * только для настройки сервера через api.
     *
     * @return String confirmationKey
     */
    public String getCallbackConfirmationCode() {
        GroupActor groupActor = vkCore.getActor();

        try {
            GetCallbackConfirmationCodeResponse result = vkCore.getVk().groups().getCallbackConfirmationCode(groupActor, groupActor.getGroupId()).execute();
            return result.getCode();
        } catch (ClientException | ApiException e) {
            logger.error("Error getting the group confirmation key:", e);
            return null;
        }
    }

}
