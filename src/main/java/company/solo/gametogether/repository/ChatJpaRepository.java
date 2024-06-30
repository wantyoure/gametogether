package company.solo.gametogether.repository;

import company.solo.gametogether.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatJpaRepository extends JpaRepository<Chat,Long> {

}
