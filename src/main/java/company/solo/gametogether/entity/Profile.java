package company.solo.gametogether.entity;

import company.solo.gametogether.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Profile extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;
    private String nicName;
    private String image;
    private String introduce;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
