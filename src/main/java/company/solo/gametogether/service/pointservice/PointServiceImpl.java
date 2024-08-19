package company.solo.gametogether.service.pointservice;

import company.solo.gametogether.entity.Member;
import company.solo.gametogether.entity.PointTransaction;
import company.solo.gametogether.repository.MemberJpaRepository;
import company.solo.gametogether.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

public class PointServiceImpl implements PointService{

    private final PointRepository pointRepository;
    private final MemberJpaRepository memberJpaRepository;

    //포인트 저장
    @Transactional(readOnly = false)
    @Override
    public void pointSave(Long memberId) {
        Optional<Member> findMember = memberJpaRepository.findById(memberId);
        if (findMember.isPresent()) {
            Member member = findMember.get();
            PointTransaction createPoint = PointTransaction.builder().dealPoint(100).history(0).member(member).build();
            pointRepository.save(createPoint);
        }
    }
    // 포인트 사용 내역 (사용내역부터)

}
