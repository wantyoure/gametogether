package company.solo.gametogether.service.friendservice;

import company.solo.gametogether.dto.frienddto.FriendListDto;
import company.solo.gametogether.entity.Friend;

import java.util.List;

public interface FriendService {

    void createFriend(Friend friend);

    List<FriendListDto> friendList(Long memberId);

    void deleteFriend(Long memberId,Long fiendId);
}
