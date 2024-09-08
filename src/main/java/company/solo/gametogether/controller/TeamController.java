package company.solo.gametogether.controller;
import company.solo.gametogether.dto.chatdto.ResponseDto;
import company.solo.gametogether.dto.chatdto.UnreadMessageDto;
import company.solo.gametogether.dto.teamdto.*;
import company.solo.gametogether.entity.Team;
import company.solo.gametogether.facade.LettuceLockChatFacade;
import company.solo.gametogether.repository.TeamJpaRepository;
import company.solo.gametogether.service.chatservice.ChatServiceImpl;
import company.solo.gametogether.service.teamservice.TeamServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {


    private final TeamJpaRepository teamJpaRepository;
    private final TeamServiceImpl teamService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatServiceImpl chatService;
    private final LettuceLockChatFacade lettuceLockChatFacade;

    //팀 생성
    @PostMapping("/create-team")
    public TeamDto createTeam(@RequestBody CreateTeamDto createTeamDto) {
        log.info("teamId={}", createTeamDto.getMemberId());
        TeamDto team = teamService.createTeam(createTeamDto);
        return team;
    }
    //팀 가입
    @PostMapping("/team-join")
    public void teamJoin (@RequestBody TeamJoinDto teamJoinDto) throws InterruptedException {
        //분산락으로 변경
        lettuceLockChatFacade.teamJoin(teamJoinDto);
        simpMessagingTemplate.convertAndSend("/topic/chat/" + teamJoinDto.getTeamId(), "가입이 되었습니다.");
    }

    //팀 방 클릭시 기존에 있던 메세지와 안 읽은 메세지 동시에 보여주기
    @GetMapping("/unread-message")
    public List<ResponseDto> unreadMessage(@RequestBody UnreadMessageDto unreadMessageDto) throws InterruptedException {
        List<ResponseDto> responseDto = lettuceLockChatFacade.unreadMessage(unreadMessageDto);
        return  responseDto;
    }

    //팀 정보확인
    @GetMapping("/{teamId}")
    public TeamDto teamGet(@PathVariable("teamId") Long id) {
        Optional<Team> findTeam = teamJpaRepository.findById(id);
        if (findTeam.isPresent()) {
            Team team = findTeam.get();
            return TeamDto.builder().teamName(team.getTeamName())
                    .teamContent(team.getTeamContent())
                    .teamCounting(team.getTeamCounting()).build();
        }
        return null; //여기도 구현
    }

    //팀 리스트 (순환참조 발생)
    @GetMapping("/list")
    public List<Team> teamList() {
        List<Team> teamList = teamService.teamList();
        return teamList;
    }

    //팀 나가기
    @DeleteMapping("/team-out")
    public String TeamDelete(@RequestBody TeamDeleteDto teamDeleteDto) {
        teamService.teamDelete(teamDeleteDto); //그 방하고 그 멤버만 나가야함
        return "방을 나갔습니다";
    }


    //팀 삭제
    @DeleteMapping("/{id}")
    public String deleteTeam(@PathVariable("id") Long id) {
        log.info("DeleteTeam={}",id);
        Optional<Team> findTeam = teamJpaRepository.findById(id);
        if (findTeam.isPresent()) {
            Team team = findTeam.get();
            teamJpaRepository.deleteById(team.getId());
        }
        return "팀이 삭제 되었습니다.";
    }
}
