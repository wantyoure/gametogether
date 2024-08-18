package company.solo.gametogether.service.teamservice;


import company.solo.gametogether.dto.teamdto.CreateTeamDto;
import company.solo.gametogether.dto.teamdto.TeamDto;
import company.solo.gametogether.dto.teamdto.TeamJoinDto;
import company.solo.gametogether.entity.Team;

import java.util.ArrayList;
import java.util.List;

public interface TeamService {

    TeamDto createTeam(CreateTeamDto createTeamDto);
    void teamJoin(TeamJoinDto teamJoinDto);

}
