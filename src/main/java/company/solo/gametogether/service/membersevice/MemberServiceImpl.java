package company.solo.gametogether.service.membersevice;

import company.solo.gametogether.dto.memberdto.*;
import company.solo.gametogether.entity.Member;
import company.solo.gametogether.jwt.JwtTokenProvider;
import company.solo.gametogether.jwt.TokenDto;
import company.solo.gametogether.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) // 읽기 전용
@RequiredArgsConstructor
@Slf4j

public class MemberServiceImpl implements MemberService {


    @Autowired
    private final MemberJpaRepository memberJpaRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    @Override
    public TokenDto signIn(String username, String password) {
        // 1. username + password 를 기반으로 Authentication(인증) 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto jwtToken = jwtTokenProvider.generateToken(authentication);
        return jwtToken;
    }

    @Transactional(readOnly = false)
    @Override
    //회원가입 로직
    public MemberDto signUp(SignUpDto signUpDto) {
        log.info("log.info ={}", signUpDto.getUsername());
        log.info("log.info ={}", signUpDto.getPassword());
        if (memberJpaRepository.existsByUsername(signUpDto.getUsername())) {
            log.info("log.info ={}", signUpDto.getUsername());
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }
        // Password 암호화
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        log.info("log.info={}", encodedPassword);
        List<String> roles = new ArrayList<>();
        roles.add("USER");  // USER 권한 부여
        return MemberDto.toDto(memberJpaRepository.save(signUpDto.toEntity(encodedPassword, roles)));
    }

    // 회원 유저이름 찾기
    @Override
    public SignInDto findByEmail(String email) {
        return memberJpaRepository.findByEmail(email);
    }

    //회원 정보 수정
    @Transactional(readOnly = false)
    @Override
    public void memberUpdate(MemberUpdate memberUpdate) {
        Optional<Member> findMember = memberJpaRepository.findById(memberUpdate.getId());
        String password = memberUpdate.getPassword();
        String phone = memberUpdate.getPhone();
        if (findMember.isPresent()) {
            Member member = findMember.get();
            member.setPassword(password);
            member.setPhone(phone);
            memberJpaRepository.save(member); // 변경된 회원 정보 저장
        }
    }
    
    // 회원 삭제
    @Transactional(readOnly = false)
    @Override
    public void memberDelete(Long id) {
        Optional<Member> findMember = memberJpaRepository.findById(id);
        if (findMember.isPresent()) {
            Member member = findMember.get();
            memberJpaRepository.delete(member);
        }
    }


    //회원 아이디 찾기
    public Member findByMember(Long id) {
        Optional<Member> member = memberJpaRepository.findById(id);
        if (member.isPresent()) {
            Member members = member.get();
            return members;
        }
        return null;
    }


}
