package company.solo.gametogether.service.chatservice;
import company.solo.gametogether.dto.chatdto.ChatDto;
import company.solo.gametogether.dto.chatdto.ResponseDto;
import company.solo.gametogether.dto.chatdto.UnreadMessageDto;
import company.solo.gametogether.entity.Chat;
import company.solo.gametogether.entity.Member;
import company.solo.gametogether.entity.Team;
import company.solo.gametogether.entity.UnReadMessage;
import company.solo.gametogether.repository.*;
import company.solo.gametogether.service.unmessageservice.UnMessageServiceImpl;
import company.solo.gametogether.session.SessionTeamIdManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    @Autowired
    private final ChatJpaRepository chatJpaRepository;
    private final TeamJpaRepository teamJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final TeamRoomJpaRepository teamRoomJpaRepository;
    private final UnReadMessageJpaRepository unReadMessageJpaRepository;
    private final UnMessageServiceImpl unMessageService;

    //채팅 메세지 저장 TODO 단순히 채팅 저장만 하는 로직이였음 그외로 readCount and 안 읽은 메세지까지 저장 해야함
    @Transactional(readOnly = false)
    @Override
    public Chat createChat(ChatDto chatDto) {
        Optional<Member> findMember = memberJpaRepository.findById(chatDto.getMemberId());
        Optional<Team> findTeam = teamJpaRepository.findById(chatDto.getTeamId());
        if (findTeam.isPresent()) {
            Team team = findTeam.get();
            Member member = findMember.get();
            int teamCounting = teamJpaRepository.findTeamCountingById(team.getId());//팀에 소속된 인원들 가져오기

            Chat createChat = Chat.builder()
                    .messageContent(chatDto.getMessageContent())
                    .team(team)
                    .member(member)
                    .username(member.getUsername())
                    .unReadMessages(new ArrayList<>())
                    .unreadCount(teamCounting - 1).build();
            Chat chat = chatJpaRepository.save(createChat);
            //이부분은 그 보낸 멤버는 ture로 만드는 로직  Builder 패턴으로 변경 예정 일단 트라이 해보고
            return chat;
        }

        return null;
    }
    //안 읽은 메세지 읽음으로 바꾸고 그리고 전체 메세지 보여주기
    @Transactional(readOnly = false)
    @Override
    public List<ResponseDto> unreadMessage(UnreadMessageDto unreadMessageDto) {
        //챗 아디를 통해서 unRedrepository를 들어가서 false인것만 가져온 다음 true로 바꿔면 되잖아
        Long memberId = unreadMessageDto.getMemberId();
        log.info("log memberid={}",memberId);
        log.info("log teamid={}",unreadMessageDto.getTeamId());
        List<Long> chatIds = chatJpaRepository.findChatIdsByTeamId(unreadMessageDto.getTeamId());
        unMessageService.updateUnreadMessages(memberId);
        // ResponseDto 객체를 담을 리스트를 생성합니다.
        List<ResponseDto> responseDtos = new ArrayList<>();
        List<Chat> all = chatJpaRepository.findAll();

        for (Chat trues : all) {
            if(!(trues.getUnreadCount() == 0)) {
                trues.setUnreadCount(trues.getUnreadCount() - 1);
            }
                // 변경된 Chat 객체를 저장합니다.
                Chat savedChat = chatJpaRepository.save(trues);
                // 저장된 Chat 객체를 ResponseDto로 변환하여 리스트에 추가합니다.
                ResponseDto responseDto = ResponseDto.builder()
                        .unReadCount(savedChat.getUnreadCount())
                        .messageContent(savedChat.getMessageContent())
                        .id(savedChat.getId())
                        .username(savedChat.getUsername())
                        .build();
                responseDtos.add(responseDto);

        }
        return responseDtos;
    }

    //팀 아이디 추출 메서드

    public String getTeamId(String id) {
        Long find = Long.valueOf(id);
        Optional<Team> findId = teamJpaRepository.findById(find);
        if (findId.isPresent()) {
            Team team = findId.get();
            String teamId = String.valueOf(team.getId());
            return teamId;
        }
        return null;
    }

    @Transactional(readOnly = false)
    //TODO 동시성 이슈 발생 가능!!
    // 채팅 메세지 저장 하고 안 읽은 메세지 저장하는 것
    public ResponseDto createChatAndHandleUnreadMessages(Long teamId, ChatDto chatDto) {
        Chat chat = createChat(chatDto); // 채팅 메세지 저장
        List<Long> findMembersId = teamRoomJpaRepository.findMemberIdsByTeamId(teamId); // 팀에 소속된 멤버들만 가져오기
        for (Long findMember : findMembersId) {
            Optional<Member> findMemberId = memberJpaRepository.findById(findMember);
            if (findMemberId.isPresent()) {
                Member member = findMemberId.get();
                boolean isRead = member.getUsername().equals(chat.getUsername()); //ture
                log.info("isRead={}",isRead);
                UnReadMessage unRead = UnReadMessage.builder()
                        .chat(chat)
                        .member(member)
                        .isRead(isRead)
                        .build();
                unReadMessageJpaRepository.save(unRead);
            }
        }
        //돌려주는 로직
        int unreadCount = chat.getUnreadCount();
        ResponseDto response = ResponseDto.builder().messageContent(chat.getMessageContent())
                .username(chat.getUsername())
                .unReadCount(unreadCount) //안 읽은 사람
                .build();
        return  response;

    }

    //넘어온 destination에서 팀Id 추출 하는 로직
    public String getTeamsId(String destination) {
        int lastIndex = destination.lastIndexOf("/");
        if (lastIndex != -1) {
            return destination.substring(lastIndex +1);
        }else {
            return "";
        }
    }

}
