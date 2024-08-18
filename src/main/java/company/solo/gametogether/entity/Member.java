package company.solo.gametogether.entity;

import company.solo.gametogether.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends BaseTimeEntity implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private long id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String nicName;
    private int age;
    private int point;

    @OneToOne(mappedBy = "member")
    private Profile profiles;

    @OneToMany(mappedBy = "member") //? 이걸 왜 바꾸는거지?
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<PointTransaction> pointTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<TeamRoom> teamRooms = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<UnReadMessage> unReadMessages = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Chat> chats = new ArrayList<>();


   //팀 룸 연관관계
    public void addTeamRoom(TeamRoom teamRoom) {
        this.teamRooms =teamRooms;
        teamRoom.addMember(this);
    }

    public void addUnReadMessage(UnReadMessage unReadMessage) {
        this.unReadMessages.add(unReadMessage);
        unReadMessage.setMember(this);
    }


    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private  List<String> roles = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
