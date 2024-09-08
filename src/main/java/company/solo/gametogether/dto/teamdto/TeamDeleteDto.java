package company.solo.gametogether.dto.teamdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDeleteDto {
    private Long teamId;
    private Long memberId;
}
