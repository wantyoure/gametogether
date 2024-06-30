package company.solo.gametogether.dto.teamdto;

import company.solo.gametogether.entity.Member;
import company.solo.gametogether.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTeamDto {

    private Long memberId;
    private String teamName;
    private String teamContent;
    private int teamCounting = 1;

}
