package company.solo.gametogether.repository;

import company.solo.gametogether.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend,Long> {


    @Query("select f from friend f where f.memberId = : memberId")
    List<Friend> findByIdAll(@Param("memberId") Long memberId);
    @Modifying
    @Query("delete from friend f where f.memberId = : memberId and f.friendId = : friendId")
    void deleteByMemberAndFriend(@Param("memberId") Friend memberId,@Param("friendId") Friend friendId);
}
