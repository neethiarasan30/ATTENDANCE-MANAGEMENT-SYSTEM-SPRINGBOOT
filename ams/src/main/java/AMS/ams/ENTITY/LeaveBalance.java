package AMS.ams.ENTITY;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_balances")
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "sick_leave_balance")
    private Integer sickLeaveBalance;

    @Column(name = "casual_leave_balance")
    private Integer casualLeaveBalance;

    @Column(name = "earned_leave_balance")
    private Integer earnedLeaveBalance;

    // Default constructor
    public LeaveBalance() {}

    // Constructor to initialize with an employee
    public LeaveBalance(Employee employee) {
        this.employee = employee;
    }

    // Getters and setters
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

    public Integer getSickLeaveBalance() {
        return sickLeaveBalance;
    }

    public void setSickLeaveBalance(Integer sickLeaveBalance) {
        this.sickLeaveBalance = sickLeaveBalance;
    }

    public Integer getCasualLeaveBalance() {
        return casualLeaveBalance;
    }

    public void setCasualLeaveBalance(Integer casualLeaveBalance) {
        this.casualLeaveBalance = casualLeaveBalance;
    }

    public Integer getEarnedLeaveBalance() {
        return earnedLeaveBalance;
    }

    public void setEarnedLeaveBalance(Integer earnedLeaveBalance) {
        this.earnedLeaveBalance = earnedLeaveBalance;
    }
}
