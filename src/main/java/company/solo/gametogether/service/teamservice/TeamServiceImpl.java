package company.solo.gametogether.service.teamservice;

import company.solo.gametogether.dto.teamdto.CreateTeamDto;
import company.solo.gametogether.dto.teamdto.TeamDeleteDto;
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


    //팀 생성
    @Transactional(readOnly = false)
    @Override
    public TeamDto createTeam(CreateTeamDto createTeamDto) {
        log.info("log={}",createTeamDto.getMemberId());
        Optional<Member> findMember = memberJpaRepository.findById(createTeamDto.getMemberId());

        if (findMember.isPresent()) {
            Member member = findMember.get();
            //팀 생성
            Team team = Team.builder().teamName(createTeamDto.getTeamName())
                    .teamContent(createTeamDto.getTeamContent())
                    .teamCounting(createTeamDto.getTeamCounting()).build(); //팀 한명 무조건 들어와야 함

            Team teams = teamJpaRepository.save(team);
            //팀 룸 생성
            TeamRoom teamRoom = TeamRoom.builder()
                    .team(team)
                    .member(member).build();
            teamRoomJpaRepository.save(teamRoom);

             TeamDto createTeam = TeamDto.builder().teamName(teams.getTeamName())
                    .teamContent(teams.getTeamContent())
                    .teamCounting(teams.getTeamCounting())
                     .id(team.getId()).build();

            return createTeam;
        }
        return null; //여기도 다시 짜야함
    }

    //팀 참여
    @Transactional(readOnly = false)
    @Override
    public void teamJoin(TeamJoinDto teamJoinDto) {
        Optional<Member> findMember = memberJpaRepository.findById(teamJoinDto.getMemberId());
        Optional<Team> findTeam = teamJpaRepository.findById(teamJoinDto.getTeamId());

        if (findTeam.isPresent() || findMember.isPresent()) {
            Member member = findMember.get();
            Team team = findTeam.get();
            team.addMember(); // 팀 참여를 했을 때
            teamJpaRepository.save(team);
            //팀 룸에 저장
            TeamRoom teamRoom = TeamRoom.builder()
                    .team(team)
                    .member(member).build();
            teamRoomJpaRepository.save(teamRoom);
        }
    }

    //팀 나갈 때
    @Transactional(readOnly = false)
    public void teamDelete(TeamDeleteDto teamDeleteDto) {
        Long memberId = teamDeleteDto.getMemberId();
        Long teamId = teamDeleteDto.getTeamId();
        Optional<TeamRoom> findRoomId = teamRoomJpaRepository.findTeamRoomIdsByMemberIdAndTeamId(memberId, teamId);// teamRoom에서 찾음
        if (findRoomId.isPresent()) {
            TeamRoom teamRoom = findRoomId.get();
            log.info("findRoomId={}",teamRoom);
            teamRoomJpaRepository.deleteByTeamRoomIds(teamRoom.getId());
            Optional<Team> findTeam = teamJpaRepository.findById(teamId);
            if (findTeam.isPresent()) {
                Team team = findTeam.get();
                teamMinusCounting(team.getId()); // 팀 나간 후 멤버 카운트
                teamJpaRepository.save(team);
            }
        }

    }


    //팀 리스트
    public List<Team> teamList() {
       return teamJpaRepository.findAll();
    }

    //한개의 팀만 보기
    public TeamDto team (Long id) {
        Optional<Team> findTeam = teamJpaRepository.findById(id);
        if (findTeam.isPresent()) {
            Team team = findTeam.get();
            return TeamDto.builder()
                    .teamName(team.getTeamName())
                    .teamContent(team.getTeamContent())
                    .teamCounting(team.getTeamCounting()).build();
        }
        return null; //여기 에러 짜야함
    }

    //팀 삭제
    @Transactional(readOnly = false)
    public void teamDelete(Long teamId) {
            teamJpaRepository.deleteById(teamId);
        }



    // 팀 입장시 유저카운트 1개 추가
    @Transactional(readOnly = false)
    public void teamAddCounting(Long id) {
        Optional<Team> findTeam = teamJpaRepository.findById(id);
        if (findTeam.isPresent()) {
            Team team = findTeam.get();
            team.addMember(); //팀을 하나 더 올리는 것
            teamJpaRepository.save(team);
        }
    }

   //팀 나갈시 유저카운트 1개 삭제 추가적으로
   @Transactional(readOnly = false)
    public void teamMinusCounting(Long id) {
       Optional<Team> findTeam = teamJpaRepository.findById(id);
       if (findTeam.isPresent()) {
           Team team = findTeam.get();
           team.minusMember();
            //팀을 하나 더 내리는 것
           teamJpaRepository.save(team);
       }
   }


}

