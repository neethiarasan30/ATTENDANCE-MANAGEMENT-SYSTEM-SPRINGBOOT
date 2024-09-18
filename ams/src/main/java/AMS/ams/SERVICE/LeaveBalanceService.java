package AMS.ams.SERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AMS.ams.ENTITY.Employee;
import AMS.ams.ENTITY.LeaveBalance;
import AMS.ams.REPOSITORY.EmployeeRepository;
import AMS.ams.REPOSITORY.LeaveBalanceRepository;

import java.util.Map;

@Service
public class LeaveBalanceService {

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Get leave balance by employee ID
    public LeaveBalance getLeaveBalance(Long employeeId) {
        return leaveBalanceRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Leave balance not found"));
    }

    // Add or update leave balances for an employee
    public LeaveBalance updateLeaveBalance(Long employeeId, Map<String, Integer> leaveBalances) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeId(employeeId)
                .orElse(new LeaveBalance(employee));

        // Set individual leave types based on the map provided
        leaveBalance.setSickLeaveBalance(leaveBalances.getOrDefault("Sick Leave", leaveBalance.getSickLeaveBalance()));
        leaveBalance.setCasualLeaveBalance(leaveBalances.getOrDefault("Casual Leave", leaveBalance.getCasualLeaveBalance()));
        leaveBalance.setEarnedLeaveBalance(leaveBalances.getOrDefault("Earned Leave", leaveBalance.getEarnedLeaveBalance()));

        return leaveBalanceRepository.save(leaveBalance);
    }
}
