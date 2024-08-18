package company.solo.gametogether.entity;

import company.solo.gametogether.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Team extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String teamName;
    private String teamContent;
    private int teamCounting; //팀 인원

    //팀 생성을 했을 시 TeamRoom도 같이 생겨야한다.
    @OneToMany(mappedBy = "team",cascade = CascadeType.ALL)
    private List<TeamRoom> teamRooms = new ArrayList<>();


    //연관관계 메소드 보류
    public void getTeam(TeamRoom teamRoom) {
        teamRooms.add(teamRoom);
        teamRoom.setTeam(this);
    }
    //생성 메소드

    // 비즈니스 로직 보류 각만 잡자

    //멤버가 추가 될 때마다 teamCounting ++ 되는 것
    // 멤버가 추가될 때 teamCounting 증가 메소드
    public void addMember() {
        this.teamCounting++;
    }
    public void minusMember() {
      this.teamCounting--;
    }
}
