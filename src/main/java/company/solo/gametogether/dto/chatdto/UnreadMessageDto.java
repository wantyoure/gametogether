package company.solo.gametogether.dto.chatdto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter

public class UnreadMessageDto {

    private Long memberId;
    private Long teamId;

}
