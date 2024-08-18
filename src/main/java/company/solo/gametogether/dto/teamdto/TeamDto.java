package company.solo.gametogether.dto.teamdto;


import company.solo.gametogether.entity.Team;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamDto {
    private Long id;
    private String teamName; //팀 이름
    private String teamContent; // 팀 컨텐츠
    private int teamCounting; //팀 인원


}
