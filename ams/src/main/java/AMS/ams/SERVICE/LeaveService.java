package AMS.ams.SERVICE;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AMS.ams.DTO.LeaveRequestResponseDTO;
import AMS.ams.ENTITY.Employee;
import AMS.ams.ENTITY.LeaveBalance;
import AMS.ams.ENTITY.LeaveRequest;
import AMS.ams.REPOSITORY.EmployeeRepository;
import AMS.ams.REPOSITORY.LeaveBalanceRepository;
import AMS.ams.REPOSITORY.LeaveRepository;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    public LeaveRequest requestLeave(Long employeeId, String leaveType, LocalDate startDate, LocalDate endDate) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<LeaveRequest> existingLeaveRequests = leaveRepository.findByEmployeeId(employeeId);
        for (LeaveRequest existingLeave : existingLeaveRequests) {
            if (datesOverlap(existingLeave.getStartDate(), existingLeave.getEndDate(), startDate, endDate)) {
                throw new RuntimeException("Leave request overlaps with an existing leave between "
                        + existingLeave.getStartDate() + " and " + existingLeave.getEndDate() + " with status: " + existingLeave.getStatus());
            }
        }

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);
        leaveRequest.setLeaveType(leaveType);
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setStatus("Pending");

        return leaveRepository.save(leaveRequest);
    }

    public LeaveRequest updateLeaveStatus(Long leaveId, String status) {
        LeaveRequest leaveRequest = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        if (status.equals("Approved")) {
            Employee employee = leaveRequest.getEmployee();
            LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeId(employee.getId())
                    .orElseThrow(() -> new RuntimeException("Leave balance not found"));

            String leaveType = leaveRequest.getLeaveType();
            int requestedDays = (int) (leaveRequest.getEndDate().toEpochDay() - leaveRequest.getStartDate().toEpochDay()) + 1;

            if (leaveType.equalsIgnoreCase("Sick Leave")) {
                int availableLeaves = leaveBalance.getSickLeaveBalance();
                if (requestedDays > availableLeaves) {
                    throw new RuntimeException("Insufficient sick leave balance");
                }
                leaveBalance.setSickLeaveBalance(availableLeaves - requestedDays);
            } else if (leaveType.equalsIgnoreCase("Casual Leave")) {
                int availableLeaves = leaveBalance.getCasualLeaveBalance();
                if (requestedDays > availableLeaves) {
                    throw new RuntimeException("Insufficient casual leave balance");
                }
                leaveBalance.setCasualLeaveBalance(availableLeaves - requestedDays);
            } else if (leaveType.equalsIgnoreCase("Earned Leave")) {
                int availableLeaves = leaveBalance.getEarnedLeaveBalance();
                if (requestedDays > availableLeaves) {
                    throw new RuntimeException("Insufficient earned leave balance");
                }
                leaveBalance.setEarnedLeaveBalance(availableLeaves - requestedDays);
            }

            leaveBalanceRepository.save(leaveBalance);
        }

        leaveRequest.setStatus(status);
        return leaveRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getLeaveRequestsByEmployeeId(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
    }

    public List<LeaveRequestResponseDTO> getAllLeaveRequests() {
        List<LeaveRequest> leaveRequests = leaveRepository.findAll();
        return leaveRequests.stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    public LeaveRequestResponseDTO convertToResponseDTO(LeaveRequest leaveRequest) {
        LeaveRequestResponseDTO dto = new LeaveRequestResponseDTO();
        dto.setId(leaveRequest.getId());
        dto.setEmployeeId(leaveRequest.getEmployee().getId());
        dto.setEmployeeName(leaveRequest.getEmployee().getName());  // Assuming Employee has a getName() method
        dto.setLeaveType(leaveRequest.getLeaveType());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setStatus(leaveRequest.getStatus());
        return dto;
    }

    private boolean datesOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return (start1.isBefore(end2) || start1.isEqual(end2)) &&
               (end1.isAfter(start2) || end1.isEqual(start2));
    }
}
