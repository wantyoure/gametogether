package company.solo.gametogether.facade;

import company.solo.gametogether.dto.chatdto.ChatDto;
import company.solo.gametogether.dto.chatdto.ResponseDto;
import company.solo.gametogether.dto.chatdto.UnreadMessageDto;
import company.solo.gametogether.dto.teamdto.TeamJoinDto;
import company.solo.gametogether.repository.RedisLockRepository;
import company.solo.gametogether.service.chatservice.ChatServiceImpl;
import company.solo.gametogether.service.teamservice.TeamServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LettuceLockChatFacade {

    private final RedisLockRepository redisLockRepository;
    private final ChatServiceImpl chatService;
    private final TeamServiceImpl teamService;

    //TODO 채팅메세지 생성
    public ResponseDto createChatAndHandleUnreadMessages(Long teamId, ChatDto chatDto) throws InterruptedException {
        System.out.println("key 받기위해 기다리는 중 =" + chatDto.getMemberId());
        while(!redisLockRepository.lock(chatDto.getMemberId())) {
            Thread.sleep(100);
            System.out.println("key 받기위해 기다리는 중 =" + chatDto.getMemberId());
        }
        try {
            //비지니스 실행
            System.out.println("key 받아서 비지니스로직 실행 중 =" + chatDto.getMemberId());
            return chatService.createChatAndHandleUnreadMessages(teamId,chatDto);
        }finally {
            redisLockRepository.unlock(chatDto.getMemberId());
        }
    }

    //TODO 안 읽은 메세지 카운트 -1 해주고 전체 메세지 보여주기
    public List<ResponseDto> unreadMessage(UnreadMessageDto unreadMessageDto) throws InterruptedException {

        while(!redisLockRepository.lock(unreadMessageDto.getMemberId())) {
            Thread.sleep(100);
            System.out.println("key 받기위해 기다리는 중 =" + unreadMessageDto.getMemberId());
        }
        try {
            //비지니스 실행
            System.out.println("key 받아서 비지니스로직 실행 중 =" + unreadMessageDto.getMemberId());
            return chatService.unreadMessage(unreadMessageDto);
        }finally {
            redisLockRepository.unlock(unreadMessageDto.getMemberId());
        }
    }

    //TODO 팀 가입할 때 4명이상 초과 되면 안 되서 락 걸어주기
    public void teamJoin (TeamJoinDto teamJoinDto) throws InterruptedException {
        while(!redisLockRepository.lock(teamJoinDto.getMemberId())) {
            Thread.sleep(100);
            System.out.println("key 받기위해 기다리는 중 =" + teamJoinDto.getMemberId());
        }
        try {
            //비지니스 실행
            System.out.println("key 받아서 비지니스로직 실행 중 =" + teamJoinDto.getMemberId());
            teamService.teamJoin(teamJoinDto);
        }finally {
            redisLockRepository.unlock(teamJoinDto.getMemberId());
        }
    }
}
