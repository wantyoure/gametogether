package company.solo.gametogether.dto.memberdto;


import company.solo.gametogether.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {

    private String username;
    private String email;
    private String password;
    private String phone;
    private String nicName;
    private int age;
    private int point;
    private List<String> roles = new ArrayList<>();

    public Member toEntity(String encodedPassword, List<String> roles) {

        return Member.builder()
                .username(username)
                .email(email)
                .password(encodedPassword)
                .phone(phone)
                .nicName(nicName)
                .age(age)
                .point(point)
                .roles(roles)
                .build();
    }


}
