package company.solo.gametogether.websokethandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import company.solo.gametogether.dto.chatdto.UnreadMessageDto;
import company.solo.gametogether.entity.Chat;
import company.solo.gametogether.service.chatservice.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MessageWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ChatServiceImpl chatService;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }
}
