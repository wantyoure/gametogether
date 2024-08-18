package company.solo.gametogether.service.membersevice;


import company.solo.gametogether.dto.memberdto.*;
import company.solo.gametogether.jwt.TokenDto;

public interface MemberService {

    public TokenDto signIn(String username, String password);
    public MemberDto signUp(SignUpDto signUpDto);

    void memberUpdate (MemberUpdate memberUpdate);

    void memberDelete(Long id);

    SignInDto findByEmail(String email);
}
