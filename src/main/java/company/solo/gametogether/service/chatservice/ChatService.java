package company.solo.gametogether.service.chatservice;

import company.solo.gametogether.dto.chatdto.ChatDto;
import company.solo.gametogether.dto.chatdto.ResponseDto;
import company.solo.gametogether.dto.chatdto.UnreadMessageDto;
import company.solo.gametogether.entity.Chat;

import java.util.List;

public interface ChatService {

    Chat createChat(ChatDto chatDto);

    List<ResponseDto> unreadMessage(UnreadMessageDto unreadMessageDto);

}
