package company.solo.gametogether.service.chatservice;

import company.solo.gametogether.dto.chatdto.ChatDto;
import company.solo.gametogether.entity.Member;
import company.solo.gametogether.entity.Team;
import company.solo.gametogether.repository.ChatJpaRepository;
import company.solo.gametogether.repository.MemberJpaRepository;
import company.solo.gametogether.repository.TeamJpaRepository;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//

class ChatServiceImplTest {

    @Mock
    MemberJpaRepository memberJpaRepository;
    @Mock
    TeamJpaRepository teamJpaRepository;
    @Mock // 가짜 객체를 쓰는 것
    ChatJpaRepository chatJpaRepository;
    @InjectMocks // 실제 객체를 쓰는 것
    ChatServiceImpl chatService;
    @Test
    void createChat() {

        //given() mock을 이용한 테스트는 행위에 집중하는 테스트이므로
        Member member = Member.builder().id(1).age(12).boards(new ArrayList<>()).email("qkrtmdgh55")
                        .phone("010").roles(new ArrayList<>()).nicName("승호박").point(0).password("1234").build();
        //when() Mock객체가 감싸고 있는 메소드가 호출되거나 호출했을 때 사용된다.
        when(memberJpaRepository.findById(any())).thenReturn(Optional.of(member));

         Team team = Team.builder().id(1L).teamName("dd").teamRooms(new ArrayList<>()).teamCounting(1).build();

        when(teamJpaRepository.findById(any())).thenReturn(Optional.of(team));
        ChatDto chatDto = new ChatDto("승호","dh",1L,1L);

        chatService.createChat(chatDto); //핵심 로직이니

        verify(chatJpaRepository,times(1)).save(any());


    }


    @Test
    @DisplayName("여기에다가 쓴다")
    void 팀이존재하지않을경우채팅이저장되면안된다 () {
        Member member = Member.builder().id(1).age(12).boards(new ArrayList<>()).email("qkrtmdgh55")
                .phone("010").roles(new ArrayList<>()).nicName("승호박").point(0).password("1234").build();
        when(memberJpaRepository.findById(any())).thenReturn(Optional.of(member));

        when(teamJpaRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        ChatDto chatDto = new ChatDto("승호","dh",1L,1L);
        chatService.createChat(chatDto); //핵심 로직이니

        verify(chatJpaRepository,times(0)).save(any());
    }
}