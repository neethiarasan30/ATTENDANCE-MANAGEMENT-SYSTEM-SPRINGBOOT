package AMS.ams.DTO;

import java.time.LocalDate;

public class PayslipRequestDTO {
    private Long employeeId;
    private LocalDate payslipDate;  // Optional field now

    public PayslipRequestDTO() {
        super();
    }

    public PayslipRequestDTO(Long employeeId, LocalDate payslipDate) {
        super();
        this.employeeId = employeeId;
        this.payslipDate = payslipDate;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getPayslipDate() {
        return payslipDate;
    }

    public void setPayslipDate(LocalDate payslipDate) {
        this.payslipDate = payslipDate;
    }    
}
