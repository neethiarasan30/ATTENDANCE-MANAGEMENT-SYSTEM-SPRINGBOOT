package AMS.ams.SERVICE;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AMS.ams.ENTITY.Admin;
import AMS.ams.ENTITY.Employee;
import AMS.ams.ENTITY.Manager;
import AMS.ams.REPOSITORY.AdminRepository;
import AMS.ams.REPOSITORY.EmployeeRepository;
import AMS.ams.REPOSITORY.ManagerRepository;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Map<String, Object> authenticate(String username, String password) {
        Map<String, Object> response = new HashMap<>();

        // Check Admin
        Optional<Admin> adminOpt = adminRepository.findByUsername(username);
        if (adminOpt.isPresent() && adminOpt.get().getPassword().equals(password)) {
            response.put("role", "admin");
            return response;
        }

        // Check Manager
        Optional<Manager> managerOpt = managerRepository.findByEmail(username);
        if (managerOpt.isPresent() && managerOpt.get().getPassword().equals(password)) {
            response.put("role", "manager");
            return response;
        }

        // Check Employee
        Optional<Employee> employeeOpt = employeeRepository.findByEmail(username);
        if (employeeOpt.isPresent() && employeeOpt.get().getPassword().equals(password)) {
            response.put("role", "employee");
            response.put("email", username);
            response.put("id", employeeOpt.get().getId()); // Include employee ID
            return response;
        }

        return null; // Return null if authentication fails
    }

    public String generateToken(String username) {
        // Implement token generation logic (e.g., JWT) if needed
        return "dummy-token"; // Replace with actual token generation logic
    }
}

