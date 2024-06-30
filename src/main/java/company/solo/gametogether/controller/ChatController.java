package company.solo.gametogether.controller;

import company.solo.gametogether.dto.chatdto.ChatDto;
import company.solo.gametogether.dto.chatdto.ResponseDto;
import company.solo.gametogether.entity.Chat;
import company.solo.gametogether.entity.Team;
import company.solo.gametogether.entity.TeamRoom;
import company.solo.gametogether.repository.TeamJpaRepository;
import company.solo.gametogether.repository.TeamRoomJpaRepository;
import company.solo.gametogether.service.chatservice.ChatService;
import company.solo.gametogether.service.chatservice.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatController {

    private final ChatServiceImpl chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{teamId}") //pub/chat/{temaId}
    @SendTo("/chat/{teamId}")
    public ResponseDto create(@RequestBody ChatDto chatDto) {
        ResponseDto chat = chatService.createChat(chatDto);
        return chat;
    }

    @MessageMapping("/chat/enter")
    public void enter(ChatRequestDto message){ // 채팅방 입장
        System.out.println("message Test" + message);
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        simpMessagingTemplate.convertAndSend("/sub/chat/" + message.getTeamId(), message);
    }


}
