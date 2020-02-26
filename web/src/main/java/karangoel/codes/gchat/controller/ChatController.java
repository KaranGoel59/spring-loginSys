package karangoel.codes.gchat.controller;

import karangoel.codes.gchat.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import static karangoel.codes.gchat.util.MessageMappings.ADD_USER;
import static karangoel.codes.gchat.util.MessageMappings.SEND_MESSAGE;
import static karangoel.codes.gchat.util.MessagePrefixes.GROUP_BROKER;

@Controller
public class ChatController {
    @MessageMapping(SEND_MESSAGE + "/{groupID}")
    @SendTo(GROUP_BROKER + "/{groupID}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping(ADD_USER + "/{groupID}")
    @SendTo(GROUP_BROKER + "/{groupID}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
