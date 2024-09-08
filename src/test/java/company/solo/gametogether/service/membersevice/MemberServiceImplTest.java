package company.solo.gametogether.service.membersevice;

import company.solo.gametogether.dto.memberdto.MemberDto;
import company.solo.gametogether.dto.memberdto.SignUpDto;
import company.solo.gametogether.entity.Member;
import company.solo.gametogether.repository.MemberJpaRepository;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
//
class MemberServiceImplTest {

    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("회원가입")
    void join() {
        MemberDto member = MemberDto.builder().id(1L).age(12).point(0).password("1234").email("010").email("qkrtmdgh55@naber.com").nicName("승호")
                .username("qkrtmdgh55").build();

        memberService.signUp(SignUpDto.builder().build());

    }

}