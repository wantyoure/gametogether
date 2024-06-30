package company.solo.gametogether.service.chatservice;

import company.solo.gametogether.dto.chatdto.ChatDto;
import company.solo.gametogether.dto.chatdto.ResponseDto;

public interface ChatService {

    ResponseDto createChat(ChatDto chatDto);

}
