package company.solo.gametogether.dto.boarddto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateBoardDto {
    private Long id;
    private String title;
    private String content;
    private String image;
}
