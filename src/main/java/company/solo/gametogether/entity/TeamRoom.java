package company.solo.gametogether.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TeamRoom {

    @Id @GeneratedValue
    @Column(name = "team_room")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Team team;

    //양방향 연관관계
    public void addMember(Member member) {
        this.member = member;
        member.getTeamRooms().add(this);
    }
    public void addTeam(Team team) {
        this.team = team;
        team.getTeamRooms().add(this);
    }


}
