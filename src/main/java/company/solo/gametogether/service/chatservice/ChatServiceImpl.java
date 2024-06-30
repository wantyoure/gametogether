package company.solo.gametogether.service.chatservice;

import company.solo.gametogether.dto.chatdto.ChatDto;
import company.solo.gametogether.dto.chatdto.ResponseDto;
import company.solo.gametogether.dto.ummessagedto.UnMessageDto;
import company.solo.gametogether.entity.Chat;
import company.solo.gametogether.entity.Member;
import company.solo.gametogether.entity.Team;
import company.solo.gametogether.entity.UnMessage;
import company.solo.gametogether.repository.ChatJpaRepository;
import company.solo.gametogether.repository.MemberJpaRepository;
import company.solo.gametogether.repository.TeamJpaRepository;
import company.solo.gametogether.repository.UnMessagesJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    @Autowired
    private final ChatJpaRepository chatJpaRepository;
    private final TeamJpaRepository teamJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final UnMessagesJpaRepository unMessagesJpaRepository;

    @Transactional(readOnly = false)
    @Override
    public ResponseDto createChat(ChatDto chatDto) {
        Optional<Member> findMember = memberJpaRepository.findById(chatDto.getMemberId());
        Optional<Team> findTeam = teamJpaRepository.findById(chatDto.getTeamId());
        if (findTeam.isPresent() && findTeam.isPresent()) {
            Team team = findTeam.get();
            Member member = findMember.get();
            int teamCounting = team.getTeamCounting();
            Chat createChat = Chat.builder()
                    .messageContent(chatDto.getMessageContent())
                    .team(team)
                    .member(member)
                    .username(member.getUsername())
                    .unMessages(new ArrayList<>())
                    .unreadCount(teamCounting).build();
            Chat chat = chatJpaRepository.save(createChat);


            UnMessage createUnMessage = UnMessage.builder()
                    .chat(chat)
                    .isRead(false)
                    .member(member)
                    .build();
            unMessagesJpaRepository.save(createUnMessage);

            ResponseDto response = ResponseDto.builder().messageContent(chat.getMessageContent())
                    .username(member.getUsername())
                    .counting(teamCounting).build();
            return response;
        }

        return null;
    }
}
