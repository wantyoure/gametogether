package company.solo.gametogether.service.teamservice;

import company.solo.gametogether.dto.teamdto.CreateTeamDto;
import company.solo.gametogether.dto.teamdto.TeamDto;
import company.solo.gametogether.dto.teamdto.TeamJoinDto;
import company.solo.gametogether.entity.Member;
import company.solo.gametogether.entity.Team;
import company.solo.gametogether.entity.TeamRoom;
import company.solo.gametogether.repository.MemberJpaRepository;
import company.solo.gametogether.repository.TeamJpaRepository;
import company.solo.gametogether.repository.TeamRoomJpaRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService {

    @Autowired
    private final TeamJpaRepository teamJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final TeamRoomJpaRepository teamRoomJpaRepository;


    @Transactional(readOnly = false)
    @Override
    public TeamDto createTeam(CreateTeamDto createTeamDto) {
        log.info("log={}",createTeamDto.getMemberId());
        Optional<Member> findMember = memberJpaRepository.findById(createTeamDto.getMemberId());
        log.info("log={}",findMember.get());

        if (findMember.isPresent()) {
            Member member = findMember.get();
            //팀 생성
            Team team = Team.builder().teamName(createTeamDto.getTeamName())
                    .teamContent(createTeamDto.getTeamContent())
                    .teamCounting(createTeamDto.getTeamCounting()).build();
            Team teams = teamJpaRepository.save(team);

            //팀 룸 생성
            TeamRoom teamRoom = TeamRoom.builder()
                    .team(team)
                    .member(member).build();

            teamRoomJpaRepository.save(teamRoom);

             TeamDto createTeam = TeamDto.builder().teamName(teams.getTeamName())
                    .teamContent(teams.getTeamContent())
                    .teamCounting(teams.getTeamCounting()).build();
            return createTeam;

        }
        return null;
    }

    //팀 참여
    @Transactional(readOnly = false)
    @Override
    public void teamJoin(TeamJoinDto teamJoinDto) {
        Optional<Member> findMember = memberJpaRepository.findById(teamJoinDto.getMemberId());
        Optional<Team> findTeam = teamJpaRepository.findById(teamJoinDto.getTeamId());
        Optional<TeamRoom> findTeamRoom = teamRoomJpaRepository.findById(teamJoinDto.getTeamRoomId());


        if (findTeam.isPresent() || findMember.isPresent() || findTeamRoom.isPresent()) {
            Member member = findMember.get();
            Team team = findTeam.get();
            TeamRoom teamRoom = findTeamRoom.get();

            // TeamRoom에 Member와 Team 추가
            teamRoom.addMember(member);
            teamRoom.addTeam(team);

            // TeamRoom 엔티티 저장
            teamRoomJpaRepository.save(teamRoom);
        }
    }
}
