package company.solo.gametogether.repository;

import company.solo.gametogether.dto.teamdto.TeamDeleteDto;
import company.solo.gametogether.entity.Member;
import company.solo.gametogether.entity.TeamRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRoomJpaRepository extends JpaRepository<TeamRoom,Long> {
    //@Query("SELECT tr.id FROM TeamRoom tr WHERE tr.member.id = :memberId AND tr.team.id = :teamId")
    @Query("SELECT tr FROM TeamRoom tr WHERE tr.member.id = :memberId AND tr.team.id = :teamId")
    Optional<TeamRoom> findTeamRoomIdsByMemberIdAndTeamId(@Param("memberId") Long memberId, @Param("teamId") Long teamId);
    @Query("SELECT tr.member.id FROM TeamRoom tr WHERE tr.team.id = :teamId") // fetch 조인 공부 다시
    List<Long> findMemberIdsByTeamId(@Param("teamId") Long teamId);

    @Modifying
    @Query("DELETE FROM TeamRoom tr WHERE tr.id IN :teamRoomIds")
    void deleteByTeamRoomIds(@Param("teamRoomIds") Long teamRoomIds);
}


