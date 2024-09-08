package company.solo.gametogether.service.unmessageservice;
import company.solo.gametogether.entity.Chat;
import company.solo.gametogether.entity.Member;
import company.solo.gametogether.entity.UnReadMessage;
import company.solo.gametogether.repository.UnReadMessageJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기 전용
@RequiredArgsConstructor
@Slf4j
public class UnMessageServiceImpl {

    private final UnReadMessageJpaRepository unReadMessageJpaRepository;

    //안 읽은 메세지 저장
    @Transactional(readOnly = false)
    public UnReadMessage unReadMessageSave(Chat chat, Member member) {
         UnReadMessage unRead = UnReadMessage.builder()
                .isRead(false)
                .member(member)
                .chat(chat).build();
        unReadMessageJpaRepository.save(unRead);
        //member.addUnReadMessage(unRead);
        return unRead;
    }

    @Transactional(readOnly = false)
    public void updateUnreadMessages(Long memberId) {
        List<UnReadMessage> findUnMessage = unReadMessageJpaRepository.findAll();
        try {
            for (UnReadMessage i : findUnMessage) {
                if (i.getMember().getId() == memberId) {
                    log.info("log I={}", i.getIsRead());
                    log.info("UnReadMessage ID: {}, Member ID: {}, isRead: {}, Checking memberId: {}",
                            i.getId(), i.getMember().getId(), i.getIsRead(), memberId);

                    // 메시지가 읽지 않았고, 멤버가 일치하는지 확인합니다.
                    if (!i.getIsRead()) {
                        log.info("Updating message with ID={} to read", i.getId());
                        i.setIsRead(true);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error updating unread messages", e);
        }
    }
}
