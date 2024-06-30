package company.solo.gametogether.dto.memberdto;

import company.solo.gametogether.entity.Member;
import lombok.*;

@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String nicName;
    private int age;
    private int point;

    static public MemberDto toDto(Member member) {
            return MemberDto.builder()
                    .username(member.getUsername())
                    .email(member.getEmail())
                    .nicName(member.getNicName())
                    .phone(member.getPhone())
                    .age(member.getAge())
                    .point(member.getPoint())
                    .build();
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .username(username)
                .email(email)
                .nicName(nicName)
                .phone(phone)
                .age(age)
                .point(point).build();
    }
}
