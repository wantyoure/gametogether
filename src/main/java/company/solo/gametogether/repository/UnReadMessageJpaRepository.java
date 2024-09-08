package company.solo.gametogether.repository;

import company.solo.gametogether.entity.UnReadMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnReadMessageJpaRepository extends JpaRepository<UnReadMessage,Long> {

    @Query("SELECT COUNT(um.member) FROM UnReadMessage um WHERE um.chat.id = :chatId")
    int countMembersByChatId(@Param("chatId") Long chatId);

    // 모든 메세지 가져오기
    @Query("SELECT um FROM UnReadMessage um WHERE um.chat.id IN :findChatId")
    List<UnReadMessage> findByUnMessage(@Param("findChatId") List<Long> findChatId);
    @Query("SELECT um FROM UnReadMessage um WHERE um.member.id = :memberId")
    List<UnReadMessage> findByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT um FROM UnReadMessage um WHERE um.chat.id IN :chatIds")
    List<UnReadMessage> findByChatIds(@Param("chatIds") List<Long> chatIds);
}
