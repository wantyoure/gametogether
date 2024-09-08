package company.solo.gametogether.session;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionTeamIdManager {
    // 클라이언트 세션을 관리하는 맵 (실제로는 세션 매니저 등을 사용할 수 있음)
    // 클라이언트 세션과 팀 아이디를 관리하는 맵
    private Map<String, String> sessionTeamIdMap = new ConcurrentHashMap<>();

    // 세션에 팀 아이디 저장
    public void setTeamId(String sessionId, String teamId) {
        sessionTeamIdMap.put(sessionId, teamId);
    }

    // 세션에서 팀 아이디 가져오기
    public String getTeamId(String sessionId) {
        return sessionTeamIdMap.get(sessionId);
    }

    // 세션에서 팀 아이디 제거
    public void removeTeamId(String sessionId) {
        String remove = sessionTeamIdMap.remove(sessionId);
    }

    // 전체 세션-팀 아이디 맵 반환 (디버깅 등에 사용)
    public Map<String, String> getSessionTeamIdMap() {
        return sessionTeamIdMap;
    }
}
