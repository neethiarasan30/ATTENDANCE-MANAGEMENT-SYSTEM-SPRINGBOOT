package AMS.ams.CONTROLLER;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import AMS.ams.DTO.LeaveRequestDTO;
import AMS.ams.DTO.LeaveRequestResponseDTO;
import AMS.ams.ENTITY.LeaveRequest;
import AMS.ams.SERVICE.LeaveService;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @PostMapping("/request")
    public ResponseEntity<LeaveRequestResponseDTO> requestLeave(@RequestBody LeaveRequestDTO leaveRequestDTO) {
        try {
            LeaveRequest leaveRequest = leaveService.requestLeave(
                    leaveRequestDTO.getEmployeeId(),
                    leaveRequestDTO.getLeaveType(),
                    leaveRequestDTO.getStartDate(),
                    leaveRequestDTO.getEndDate()
            );
            LeaveRequestResponseDTO responseDTO = leaveService.convertToResponseDTO(leaveRequest);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/approve/{leaveId}")
    public ResponseEntity<LeaveRequestResponseDTO> approveLeave(@PathVariable Long leaveId) {
        try {
            LeaveRequest leaveRequest = leaveService.updateLeaveStatus(leaveId, "Approved");
            LeaveRequestResponseDTO responseDTO = leaveService.convertToResponseDTO(leaveRequest);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/reject/{leaveId}")
    public ResponseEntity<LeaveRequestResponseDTO> rejectLeave(@PathVariable Long leaveId) {
        try {
            LeaveRequest leaveRequest = leaveService.updateLeaveStatus(leaveId, "Rejected");
            LeaveRequestResponseDTO responseDTO = leaveService.convertToResponseDTO(leaveRequest);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getLeaveRequestsByEmployeeId(@PathVariable Long employeeId) {
        List<LeaveRequest> leaveRequests = leaveService.getLeaveRequestsByEmployeeId(employeeId);
        List<LeaveRequestResponseDTO> responseDTOs = leaveRequests.stream()
                .map(leaveService::convertToResponseDTO)
                .toList();
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getAllLeaveRequests() {
        List<LeaveRequestResponseDTO> leaveRequests = leaveService.getAllLeaveRequests();
        return ResponseEntity.ok(leaveRequests);
    }
}
