package company.solo.gametogether.dto.ummessagedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnMessageDto {

    private Long memberId;
    private Long chatId;
    boolean isRead;
}
