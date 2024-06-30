package company.solo.gametogether.dto.teamdto;


import company.solo.gametogether.entity.Team;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamDto {

    private String teamName;
    private String teamContent;
    private int teamCounting; //팀 인원


}
