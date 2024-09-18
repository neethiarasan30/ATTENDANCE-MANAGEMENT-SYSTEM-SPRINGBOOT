package AMS.ams.REPOSITORY;

import org.springframework.data.jpa.repository.JpaRepository;

import AMS.ams.ENTITY.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}