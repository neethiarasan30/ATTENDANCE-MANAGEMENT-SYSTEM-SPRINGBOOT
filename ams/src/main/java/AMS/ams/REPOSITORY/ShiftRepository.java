package AMS.ams.REPOSITORY;

import org.springframework.data.jpa.repository.JpaRepository;
import AMS.ams.ENTITY.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
}
