package company.solo.gametogether.dto.chatdto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto {

    private String username;
    private String messageContent;
    private int counting;

}
