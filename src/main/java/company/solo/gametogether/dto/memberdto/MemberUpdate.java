package company.solo.gametogether.dto.memberdto;

import lombok.Data;

@Data
public class MemberUpdate {
    //비밀번호
    //전화번호
    private Long id;
    private String password;
    private String phone;

}
