package company.solo.gametogether.service.friendservice;

import company.solo.gametogether.dto.boarddto.CreateBoardDto;
import company.solo.gametogether.dto.frienddto.CreateFriendDto;
import company.solo.gametogether.dto.frienddto.FriendListDto;
import company.solo.gametogether.entity.Friend;
import company.solo.gametogether.entity.Member;
import company.solo.gametogether.repository.FriendRepository;
import company.solo.gametogether.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class FriendServiceImpl implements FriendService{

    private final FriendRepository friendRepository;
    private final MemberJpaRepository memberJpaRepository;

    //친구 추가 로직

    @Transactional
    @Override
    public void createFriend(Friend friend) {
        if (!friend.getMemberId().equals(friend.getFriendId())) {

            friendRepository.save(friend);
        }
        if (!memberJpaRepository.existsById(friend.getMemberId()) || !memberJpaRepository.existsById(friend.getId())) {
            throw new IllegalArgumentException("존재하지 않은 회원입니다"); //TODO 이부분을 이렇게 써도 될까? 아니면 다른 방법으로 exception을 해야할까?
        }

    }

    //친구 목록 //TODO 추가로 프로필을 가져다가 return 해줘야하는데 임시로 해봄
    @Override
    public List<FriendListDto> friendList(Long memberId) {
        List<Friend> findFriend = friendRepository.findByIdAll(memberId);
        List<FriendListDto> friendListDtos = findFriend.stream()
                .map(friend -> memberJpaRepository.findById(friend.getFriendId()))
                .filter(Optional::isPresent)
                .map(optionalMember -> {
                    Member member = optionalMember.get();
                    return new FriendListDto(member.getUsername());
                })
                .collect(Collectors.toList());
    return friendListDtos;
    }

    //친구 삭제
    @Transactional(readOnly = false)
    @Override
    public void deleteFriend(Long memberId,Long friendId) {
        Optional<Friend> findMember = friendRepository.findById(memberId);
        Optional<Friend> findFriend = friendRepository.findById(friendId);
        if (findMember.isPresent() || findFriend.isPresent()) {
            Friend member = findMember.get();
            Friend friend = findFriend.get();
            friendRepository.deleteByMemberAndFriend(member,friend);
        }
    }


}
