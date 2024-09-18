package AMS.ams.REPOSITORY;

import org.springframework.data.jpa.repository.JpaRepository;
import AMS.ams.ENTITY.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
}
