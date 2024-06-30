package company.solo.gametogether.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenDto { //Jwt DTO

    private String grantType; //승인 타입
    private String accessToken; //연결 토큰
    private String refreshToken; //리플리쉬 토큰

}
