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

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Team team;

    //양방향 연관관계
    public void addMember(Member member) {
        this.member = member;
        member.getTeamRooms().add(this);
    }

    public void removeMember() {
        if (this.member != null) {
            this.member.getTeamRooms().remove(this);
            this.member = null;
        }
    }
    public void addTeam(Team team) {
        this.team = team;
        team.getTeamRooms().add(this);
    }

    public void removeTeam() {
        if (this.team != null) {
            this.team.getTeamRooms().remove(this);
            this.team = null;
        }
    }




}
