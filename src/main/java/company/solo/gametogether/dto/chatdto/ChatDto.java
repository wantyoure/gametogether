package company.solo.gametogether.dto.chatdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

        private String username;
        private String messageContent;
        private Long teamId;
        private Long memberId;

}
