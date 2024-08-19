package company.solo.gametogether.entity;

import company.solo.gametogether.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PointTransaction extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "point_transaction_id")
    private Long id;
    private int dealPoint;
    private int history; //사용 내역

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
