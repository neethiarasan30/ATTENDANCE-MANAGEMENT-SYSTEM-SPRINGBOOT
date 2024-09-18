package AMS.ams.REPOSITORY;

import AMS.ams.ENTITY.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
