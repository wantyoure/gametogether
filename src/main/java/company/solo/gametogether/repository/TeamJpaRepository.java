package company.solo.gametogether.repository;

import company.solo.gametogether.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TeamJpaRepository extends JpaRepository<Team,Long> {


    @Query("SELECT t.teamCounting FROM Team t WHERE t.id = :id")
    int findTeamCountingById(@Param("id") Long id);


}
