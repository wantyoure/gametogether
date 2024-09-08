package company.solo.gametogether.controller;

import company.solo.gametogether.dto.chatdto.ChatDto;
import company.solo.gametogether.dto.chatdto.ResponseDto;
import company.solo.gametogether.facade.LettuceLockChatFacade;
import company.solo.gametogether.service.chatservice.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final LettuceLockChatFacade lettuceLockChatFacade;


    //팀에게 메세지 보내기  //TODO 여기에 추가로 만약 user가 채팅방에 있으면 바로 read로  바꾸고 그리고 읽은 메세지 카운팅도 -1 해주는게 맞잖아
    @MessageMapping("/chat/{teamId}") //app/chat/{teamId}
    public void create(@DestinationVariable("teamId") Long teamId, @RequestBody ChatDto chatDto) throws InterruptedException {
        ResponseDto chat = lettuceLockChatFacade.createChatAndHandleUnreadMessages(teamId,chatDto);
        simpMessagingTemplate.convertAndSend("/topic/chat/"+teamId,chat); //여기로 보내기 /topic/chat/1
    }


}
