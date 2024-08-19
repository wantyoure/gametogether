package company.solo.gametogether.controller;

import company.solo.gametogether.dto.frienddto.CreateFriendDto;
import company.solo.gametogether.dto.frienddto.FriendListDto;
import company.solo.gametogether.entity.Friend;
import company.solo.gametogether.service.friendservice.FriendServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendServiceImpl friendService;

    //친구 추가
    @PostMapping()
    public void createFriend(@RequestBody Friend friend) {
        friendService.createFriend(friend);
    }

    // 내친구 리스트
    @GetMapping("/list/{memberId}") //pathvariable 로 처리
    public List<FriendListDto> friendList(@PathVariable("memberId") Long memberId) {
        return friendService.friendList(memberId);
    }

    // 친구 삭제
    @DeleteMapping("/{memberId}")
    public void deleteFriend(@PathVariable("memberId") Long memberId, @RequestBody Long friendId) {
        friendService.deleteFriend(memberId,friendId);
    }
}
