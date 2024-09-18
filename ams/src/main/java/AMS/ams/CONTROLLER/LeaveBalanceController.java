package AMS.ams.CONTROLLER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import AMS.ams.ENTITY.LeaveBalance;
import AMS.ams.SERVICE.LeaveBalanceService;

import java.util.Map;

@RestController
@RequestMapping("/api/leave-balances")
public class LeaveBalanceController {

    @Autowired
    private LeaveBalanceService leaveBalanceService;

    // Endpoint to get leave balance by employee ID
    @GetMapping("/{employeeId}")
    public ResponseEntity<LeaveBalance> getLeaveBalance(@PathVariable Long employeeId) {
        LeaveBalance leaveBalance = leaveBalanceService.getLeaveBalance(employeeId);
        return ResponseEntity.ok(leaveBalance);
    }

    // Endpoint to update leave balances for an employee
    @PutMapping("/updateLeaveBalance/{employeeId}")
    public ResponseEntity<LeaveBalance> updateLeaveBalance(@PathVariable Long employeeId, @RequestBody Map<String, Integer> leaveBalances) {
        LeaveBalance updatedLeaveBalance = leaveBalanceService.updateLeaveBalance(employeeId, leaveBalances);
        return ResponseEntity.ok(updatedLeaveBalance);
    }
}
