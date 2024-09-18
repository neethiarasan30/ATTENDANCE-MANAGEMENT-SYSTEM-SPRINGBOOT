package AMS.ams.REPOSITORY;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import AMS.ams.ENTITY.LeaveRequest;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {
	
    List<LeaveRequest> findByEmployeeIdAndStatus(Long employeeId, String status);
    
    List<LeaveRequest> findByEmployeeId(Long employeeId);


}
