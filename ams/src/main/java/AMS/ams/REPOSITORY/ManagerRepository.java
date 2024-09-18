package AMS.ams.REPOSITORY;

import org.springframework.data.jpa.repository.JpaRepository;
import AMS.ams.ENTITY.Manager;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByEmail(String email);  
}
