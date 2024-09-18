package AMS.ams.REPOSITORY;

import org.springframework.data.jpa.repository.JpaRepository;

import AMS.ams.ENTITY.Designation;

public interface DesignationRepository extends JpaRepository<Designation, Long> {
}