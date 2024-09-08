package company.solo.gametogether.websokethandler;

import company.solo.gametogether.jwt.JwtTokenProvider;
import company.solo.gametogether.service.chatservice.ChatServiceImpl;
import company.solo.gametogether.session.SessionTeamIdManager;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
@Setter
public class CustomChannelInterceptor implements ChannelInterceptor {


    private final ChatServiceImpl chatService;
    private final JwtTokenProvider jwtTokenProvider; // 이거 나중에 jwt 설정 다시
    private final SessionTeamIdManager sessionAndTeam;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId(); //연결된 세션
        String memberId = accessor.getFirstNativeHeader("memberId");
        log.info("FistMemberId={}", memberId);

        if (StompCommand.CONNECT == accessor.getCommand()) {// 웹소켓 연결시
            log.info("Connect={}",accessor.getCommand());
            Object memberId1 = accessor.getSessionAttributes().get("memberId");
            String memberId2 = accessor.getFirstNativeHeader("memberId");
            log.info("WebSocketMemberId session={}",memberId2);
        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {  // 웹소켓 구독시
            String member2=accessor.getFirstNativeHeader("memberId");
            log.info("SUBSCRIBEMember={}",member2);
        } else if (StompCommand.DISCONNECT == accessor.getCommand()) { // 웹소켓 끊어지면
            // 연결이 끊어지면 모든 활성화된 멤버 정보 삭제
            accessor.getSessionAttributes().clear();
            log.info("Disconnected. Cleared all active members.");
            // 세션 ID를 통해 팀 ID 조회
            String findTeamId = sessionAndTeam.getTeamId(sessionId);
            if (findTeamId != null) {
                try {
                    Long teamId = Long.parseLong(findTeamId);
                    log.info("세션 아이디 삭제시 findTeamId={}, teamId={},result ={}", findTeamId, teamId);
                    // 세션 ID 삭제
                    //sessionAndTeam.removeTeamId(sessionId);
                } catch (NumberFormatException e) {
                    log.error("Failed to parse teamId: {}", findTeamId);
                    // 예외 처리: teamId 파싱 실패
                }
            } else {
                log.warn("No teamId found for sessionId={}", sessionId);
                // 경고: 세션 ID에 해당하는 팀 ID가 없음
            }
        }
        return message;
    }
}
