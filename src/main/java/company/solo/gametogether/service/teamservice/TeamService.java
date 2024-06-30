package company.solo.gametogether.service.teamservice;


import company.solo.gametogether.dto.teamdto.CreateTeamDto;
import company.solo.gametogether.dto.teamdto.TeamDto;
import company.solo.gametogether.dto.teamdto.TeamJoinDto;

public interface TeamService {

    TeamDto createTeam(CreateTeamDto createTeamDto);
    void teamJoin(TeamJoinDto teamJoinDto);
}
