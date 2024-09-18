package AMS.ams.ENTITY;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class EmployeeAcknowledgment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER) // Ensure employee details are eagerly fetched
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER) // Ensure policy details are eagerly fetched
    @JoinColumn(name = "policy_id")
    private Policy policy;

    private LocalDate acknowledgmentDate;

	public EmployeeAcknowledgment() {
		super();
	}

	public EmployeeAcknowledgment(Long id, Employee employee, Policy policy, LocalDate acknowledgmentDate) {
		super();
		this.id = id;
		this.employee = employee;
		this.policy = policy;
		this.acknowledgmentDate = acknowledgmentDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public LocalDate getAcknowledgmentDate() {
		return acknowledgmentDate;
	}

	public void setAcknowledgmentDate(LocalDate acknowledgmentDate) {
		this.acknowledgmentDate = acknowledgmentDate;
	}
}
