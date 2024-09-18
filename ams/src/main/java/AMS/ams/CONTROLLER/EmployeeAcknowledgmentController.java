package AMS.ams.CONTROLLER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AMS.ams.ENTITY.EmployeeAcknowledgment;
import AMS.ams.SERVICE.EmployeeAcknowledgmentService;

@RestController
@RequestMapping("/api/employee-acknowledgments")
public class EmployeeAcknowledgmentController {

    @Autowired
    private EmployeeAcknowledgmentService acknowledgmentService;

    @PostMapping
    public ResponseEntity<EmployeeAcknowledgment> createAcknowledgment(@RequestBody EmployeeAcknowledgment acknowledgment) {
        EmployeeAcknowledgment savedAcknowledgment = acknowledgmentService.createAcknowledgment(acknowledgment);
        return ResponseEntity.ok(savedAcknowledgment);
    }
}
