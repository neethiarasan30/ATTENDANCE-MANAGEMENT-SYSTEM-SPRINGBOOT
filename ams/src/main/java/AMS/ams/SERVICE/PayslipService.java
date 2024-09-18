package AMS.ams.SERVICE;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import AMS.ams.ENTITY.Employee;
import AMS.ams.ENTITY.LeaveRequest;
import AMS.ams.ENTITY.Payslip;
import AMS.ams.EXCEPTION.ResourceNotFoundException;
import AMS.ams.REPOSITORY.EmployeeRepository;
import AMS.ams.REPOSITORY.LeaveRepository;
import AMS.ams.REPOSITORY.PayslipRepository;

@Service
public class PayslipService {
    
    @Autowired
    private PayslipRepository payslipRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    public Payslip generatePayslip(Long employeeId, LocalDate payslipDate) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Set payslip date to current date if not provided
        if (payslipDate == null) {
            payslipDate = LocalDate.now();
        }

        BigDecimal basicSalary = employee.getBasicSalary();
        BigDecimal allowances = calculateAllowances(employee);
        BigDecimal deductions = calculateDeductions(employeeId, payslipDate);
        BigDecimal netPay = basicSalary.add(allowances).subtract(deductions);

        Payslip payslip = new Payslip();
        payslip.setEmployee(employee);
        payslip.setPayslipDate(payslipDate);
        payslip.setBasicSalary(basicSalary);
        payslip.setAllowances(allowances);
        payslip.setDeductions(deductions);
        payslip.setNetPay(netPay);

        return payslipRepository.save(payslip);
    }

    private BigDecimal calculateAllowances(Employee employee) {
        // Logic for calculating allowances
        return new BigDecimal("1000.00"); // Placeholder
    }

    private BigDecimal calculateDeductions(Long employeeId, LocalDate payslipDate) {
        List<LeaveRequest> approvedLeaves = leaveRepository.findByEmployeeIdAndStatus(employeeId, "Approved");

        int totalApprovedLeaveDays = 0;
        for (LeaveRequest leave : approvedLeaves) {
            if (leave.getStartDate().getMonth().equals(payslipDate.getMonth())) {
                int leaveDays = (int) (leave.getEndDate().toEpochDay() - leave.getStartDate().toEpochDay()) + 1;
                totalApprovedLeaveDays += leaveDays;
            }
        }

        BigDecimal leaveDeduction = new BigDecimal(totalApprovedLeaveDays * 500);
        
        // Add any additional deductions here
        return leaveDeduction;
    }

    public List<Payslip> getPayslipsByEmployee(Long employeeId) {
        return payslipRepository.findByEmployeeId(employeeId);
    }
    
   
}
