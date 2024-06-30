package company.solo.gametogether.controller;

import company.solo.gametogether.dto.chatdto.ChatDto;
import company.solo.gametogether.dto.teamdto.CreateTeamDto;
import company.solo.gametogether.dto.teamdto.TeamDto;
import company.solo.gametogether.dto.teamdto.TeamJoinDto;
import company.solo.gametogether.repository.TeamJpaRepository;
import company.solo.gametogether.service.teamservice.TeamServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    @Autowired
    private final TeamJpaRepository teamJpaRepository;
    private final TeamServiceImpl teamService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    //팀 생성
    @PostMapping("/create-team")
    public TeamDto createTeam(@RequestBody CreateTeamDto createTeamDto){
        log.info("log teamGetId={}",createTeamDto.getMemberId());
        TeamDto team = teamService.createTeam(createTeamDto);
        return team;
    }
    //팀 입장
    @PostMapping("/join")
    public String teamJoin(@RequestBody TeamJoinDto teamJoinDto) {
        teamService.teamJoin(teamJoinDto);
        ChatRequestDto message = new ChatRequestDto();
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        simpMessagingTemplate.convertAndSend("/sub/chat/" + message.getTeamId(), message);
        return "팀에 가입이 되었습니다";
    }






}
