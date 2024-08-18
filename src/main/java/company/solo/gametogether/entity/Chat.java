package company.solo.gametogether.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import company.solo.gametogether.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Chat extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;
    private String username;
    private String messageContent;
    @Column(nullable = false)
    private int unreadCount; //안 읽은 사람의 개수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "chat")
    private List<UnReadMessage> unReadMessages = new ArrayList<>();


    //TODO



}
