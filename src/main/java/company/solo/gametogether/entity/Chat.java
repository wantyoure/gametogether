package company.solo.gametogether.entity;

import company.solo.gametogether.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Chat extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;
    private String username;
    private String  messageContent;
    private int unreadCount; //안 읽은 사람의 개수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "chat")
    private List<UnMessage> unMessages = new ArrayList<>();
}
