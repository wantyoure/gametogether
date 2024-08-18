package company.solo.gametogether.controller;

import company.solo.gametogether.dto.chatdto.ChatDto;
import company.solo.gametogether.dto.chatdto.ResponseDto;
import company.solo.gametogether.dto.chatdto.UnreadMessageDto;
import company.solo.gametogether.dto.teamdto.TeamJoinDto;
import company.solo.gametogether.entity.Chat;
import company.solo.gametogether.repository.ChatJpaRepository;
import company.solo.gametogether.service.chatservice.ChatServiceImpl;
import company.solo.gametogether.service.teamservice.TeamServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatServiceImpl chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final TeamServiceImpl teamService;


    //팀에게 메세지 보내기  //TODO 여기에 추가로 만약 user가 채팅방에 있으면 바로 read로  바꾸고 그리고 읽은 메세지 카운팅도 -1 해주는게 맞잖아
    @MessageMapping("/chat/{teamId}") //app/chat/{teamId}
    public void create(@DestinationVariable("teamId") Long teamId, @RequestBody ChatDto chatDto) {
        ResponseDto chat = chatService.createChatAndHandleUnreadMessages(teamId,chatDto);
        simpMessagingTemplate.convertAndSend("/topic/chat/"+teamId,chat); //여기로 보내기 /topic/chat/1
    }

    //팀 입장 //커넥이 연결이 되어야 되니까 여기에 두었다 //TODO 이거 약간 이상한데 다시 생각보자
    @MessageMapping("/chat/team-join")
    public void teamJoin (@RequestBody TeamJoinDto teamJoinDto){
        teamService.teamJoin(teamJoinDto);
        simpMessagingTemplate.convertAndSend("/topic/chat/" + teamJoinDto.getTeamId(), "가입이 되었습니다.");
    }

    //팀 방 클릭시 기존에 있던 메세지와 안 읽은 메세지 동시에 보여주기
    @GetMapping("/chat/unread-message")
    public List<ResponseDto> unreadMessage(@RequestBody UnreadMessageDto unreadMessageDto) {
        List<ResponseDto> responseDto = chatService.unreadMessage(unreadMessageDto);
        return  responseDto;

    }
}
