package company.solo.gametogether.controller;


import company.solo.gametogether.dto.memberdto.*;
import company.solo.gametogether.entity.Member;
import company.solo.gametogether.jwt.TokenDto;
import company.solo.gametogether.repository.MemberJpaRepository;
import company.solo.gametogether.service.membersevice.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberServiceImpl memberServiceImpl;
    private final MemberJpaRepository memberJpaRepository;


    //회원 테스트
    @PostMapping("/test")
    public String test() {
        return "테스트 완료";
    }

    //회원 가입
    //TODO 사실상 동시성 이슈가 발생할 수 있지만 이메일을 유니크하게 만들면 어느정도 막을 수 있다
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto) {
        log.info("log={}", signUpDto.getUsername());
        MemberDto saveMemberDto = memberServiceImpl.signUp(signUpDto);
        String nicName = saveMemberDto.getNicName();
        return new ResponseEntity<>(nicName, HttpStatus.OK);

    }

    //로그인
    @PostMapping("/sign-in")
    public TokenDto signIn(@RequestBody SignInDto signInDto) {
        String username = signInDto.getUsername();
        String password = signInDto.getPassword();

        TokenDto jwtToken = memberServiceImpl.signIn(username, password);
        log.info("request username = {}, password = {}", username, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    //아이디 찾기
    @GetMapping("/find-user")
    public String findUserName(@RequestBody String email) {
        SignInDto userNames = memberServiceImpl.findByEmail(email);
        System.out.println("유저네임 =" + userNames);
        return userNames.getUsername();
    }


    //회원 정보 수정
    @PutMapping("/{id}")
    public String memberUpdate( @PathVariable("id") Long id,@RequestBody MemberUpdate memberUpdate) {
        memberUpdate.setId(id);
        memberServiceImpl.memberUpdate(memberUpdate);
        return "회원 변경 완료되었습니다";
    }

    //회원 탈퇴
    @DeleteMapping("/{id}")
    public String memberDelete(@PathVariable("id")Long id) {
        memberServiceImpl.memberDelete(id);
        return "회원이 삭제 되었습니다";
    }


    //회원이 소속되어 있는 team방 조회
    //@GetMapping("/{id}/list-team")

    //회원 전체 조회
    @GetMapping("/find-all")
    public List<Member> list() {
        return memberJpaRepository.findAll();
    }


}
