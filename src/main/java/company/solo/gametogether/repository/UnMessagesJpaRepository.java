package company.solo.gametogether.repository;

import company.solo.gametogether.entity.UnMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnMessagesJpaRepository extends JpaRepository<UnMessage,Long> {
}
