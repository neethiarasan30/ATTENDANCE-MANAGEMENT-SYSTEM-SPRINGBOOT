package AMS.ams.REPOSITORY;

import AMS.ams.ENTITY.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeIdAndDateBetween(Long employeeId, LocalDateTime startDate, LocalDateTime endDate);
}
