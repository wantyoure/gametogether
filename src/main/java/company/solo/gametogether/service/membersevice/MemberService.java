package company.solo.gametogether.service.membersevice;


import company.solo.gametogether.dto.memberdto.MemberDto;
import company.solo.gametogether.dto.memberdto.MemberFindUserNameDto;
import company.solo.gametogether.dto.memberdto.MemberUpdate;
import company.solo.gametogether.dto.memberdto.SignUpDto;
import company.solo.gametogether.jwt.TokenDto;

public interface MemberService {

    public TokenDto signIn(String username, String password);
    public MemberDto signUp(SignUpDto signUpDto);

    MemberFindUserNameDto findUserNames(String email);
    void memberUpdate (MemberUpdate memberUpdate);

    void memberDelete(Long id);
}
