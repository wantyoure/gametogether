package company.solo.gametogether.repository;

import company.solo.gametogether.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TeamJpaRepository extends JpaRepository<Team,Long> {


}
