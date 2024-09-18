package AMS.ams.SERVICE;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AMS.ams.ENTITY.Employee;
import AMS.ams.ENTITY.EmployeeAcknowledgment;
import AMS.ams.ENTITY.Policy;
import AMS.ams.EXCEPTION.ResourceNotFoundException;
import AMS.ams.REPOSITORY.EmployeeAcknowledgmentRepository;
import AMS.ams.REPOSITORY.EmployeeRepository;
import AMS.ams.REPOSITORY.PolicyRepository;

@Service
public class EmployeeAcknowledgmentService {

    @Autowired
    private EmployeeAcknowledgmentRepository acknowledgmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    
    @Autowired
    PolicyRepository policyRepository;
    
    public EmployeeAcknowledgment createAcknowledgment(EmployeeAcknowledgment acknowledgment) {
        // Assuming acknowledgment.getEmployee() and acknowledgment.getPolicy() are not null and have correct IDs
        Employee employee = employeeRepository.findById(acknowledgment.getEmployee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        Policy policy = policyRepository.findById(acknowledgment.getPolicy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));

        acknowledgment.setEmployee(employee);
        acknowledgment.setPolicy(policy);
        acknowledgment.setAcknowledgmentDate(LocalDate.now()); // Automatically set acknowledgment date

        return acknowledgmentRepository.save(acknowledgment);
    }

}
