package company.solo.gametogether.repository;

import company.solo.gametogether.entity.Chat;
import company.solo.gametogether.entity.Member;
import company.solo.gametogether.entity.UnReadMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatJpaRepository extends JpaRepository<Chat,Long> {
    // 멤버와 팀아이디로 채팅 아이디 찾기

    @Query("SELECT c.id FROM Chat c WHERE c.team.id = :teamId")
    List<Long> findChatIdsByTeamId(@Param("teamId") Long teamId);

}
