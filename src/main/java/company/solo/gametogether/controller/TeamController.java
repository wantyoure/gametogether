package company.solo.gametogether.controller;
import company.solo.gametogether.dto.teamdto.*;
import company.solo.gametogether.entity.Team;
import company.solo.gametogether.repository.TeamJpaRepository;
import company.solo.gametogether.service.teamservice.TeamServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    //팀 생성
    @PostMapping("/create-team")
    public TeamDto createTeam(@RequestBody CreateTeamDto createTeamDto) {
        log.info("teamId={}", createTeamDto.getMemberId());
        TeamDto team = teamService.createTeam(createTeamDto);
        return team;
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
