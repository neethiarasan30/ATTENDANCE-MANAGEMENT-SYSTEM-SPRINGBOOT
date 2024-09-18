package AMS.ams.ENTITY;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.Duration;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column
    private Double workedHours; // Store worked hours

    @Column(nullable = false)
    private String status = "Not marked"; // Default status

    // Constructors
    public Attendance() {}

    public Attendance(Employee employee, LocalDateTime checkInTime, LocalDateTime checkOutTime, LocalDateTime date, String status) {
        this.employee = employee;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.date = date;
        this.status = status;
        this.workedHours = calculateWorkedHours(checkInTime, checkOutTime); // Calculate hours
    }

    // Getters and Setters
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

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
        this.workedHours = calculateWorkedHours(this.checkInTime, checkOutTime); // Update worked hours
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(Double workedHours) {
        this.workedHours = workedHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method to calculate worked hours
    public Double calculateWorkedHours(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkIn != null && checkOut != null) {
            Duration duration = Duration.between(checkIn, checkOut);
            long minutesWorked = duration.toMinutes();
            return minutesWorked / 60.0; // Convert minutes to hours with precision
        }
        return 0.0;
    }
}
