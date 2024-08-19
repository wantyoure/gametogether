package company.solo.gametogether.dto.frienddto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateFriendDto {

    private Long memberId;
    private Long friendId;

}
