package company.solo.gametogether.repository;

import company.solo.gametogether.entity.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;

public interface PointRepository extends JpaRepository<PointTransaction,Long> {
}
