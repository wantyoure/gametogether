package company.solo.gametogether.repository;
import company.solo.gametogether.dto.memberdto.MemberFindUserNameDto;
import company.solo.gametogether.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    boolean existsByUsername(String username);
    @Query("select new company.solo.gametogether.dto.memberdto.MemberFindUserNameDto(m.username) FROM Member m WHERE m.email = :email")
    MemberFindUserNameDto findByUsernames(@Param("email") String email);

    List<Member> findAll();






}
