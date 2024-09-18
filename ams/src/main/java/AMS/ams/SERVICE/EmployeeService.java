package AMS.ams.SERVICE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AMS.ams.ENTITY.Department;
import AMS.ams.ENTITY.Designation;
import AMS.ams.ENTITY.Employee;
import AMS.ams.ENTITY.Manager;
import AMS.ams.ENTITY.Shift;
import AMS.ams.REPOSITORY.DepartmentRepository;
import AMS.ams.REPOSITORY.DesignationRepository;
import AMS.ams.REPOSITORY.EmployeeRepository;
import AMS.ams.REPOSITORY.ManagerRepository;
import AMS.ams.REPOSITORY.ShiftRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ShiftRepository shiftRepository;
    

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee saveEmployee(Employee employee) {
        // Validate and fetch related entities
        Department department = validateAndFetchDepartment(employee.getDepartment());
        Designation designation = validateAndFetchDesignation(employee.getDesignation());
        Manager manager = validateAndFetchManager(employee.getManager());
        Shift shift = validateAndFetchShift(employee.getShift());

        // Set fetched entities back to employee
        employee.setDepartment(department);
        employee.setDesignation(designation);
        employee.setManager(manager);
        employee.setShift(shift);

        // Save and return the employee
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            existingEmployee.setFirstName(employeeDetails.getFirstName());
            existingEmployee.setLastName(employeeDetails.getLastName());
            existingEmployee.setEmail(employeeDetails.getEmail());
            existingEmployee.setPhoneNumber(employeeDetails.getPhoneNumber());
            existingEmployee.setDepartment(employeeDetails.getDepartment());
            existingEmployee.setDesignation(employeeDetails.getDesignation());
            existingEmployee.setManager(employeeDetails.getManager());
            existingEmployee.setShift(employeeDetails.getShift()); // Update shift
            return employeeRepository.save(existingEmployee);
        } else {
            return null;
        }
    }

    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    
    
    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email).orElse(null);
    }
    
    
    private Department validateAndFetchDepartment(Department department) {
        System.out.println("Validating Department ID: " + department.getId());

        if (department == null || department.getId() == null) {
            throw new IllegalArgumentException("Department must be provided with a valid ID");
        }
        System.out.println("Validating Department ID: " + department.getId());
        return departmentRepository.findById(department.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));
    }


    private Designation validateAndFetchDesignation(Designation designation) {
        if (designation == null || designation.getId() == null) {
            throw new IllegalArgumentException("Designation must be provided with a valid ID");
        }
        return designationRepository.findById(designation.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid designation ID"));
    }

    private Manager validateAndFetchManager(Manager manager) {
        if (manager == null || manager.getId() == null) {
            throw new IllegalArgumentException("Manager must be provided with a valid ID");
        }
        return managerRepository.findById(manager.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid manager ID"));
    }

    private Shift validateAndFetchShift(Shift shift) {
        if (shift == null || shift.getId() == null) {
            throw new IllegalArgumentException("Shift must be provided with a valid ID");
        }
        return shiftRepository.findById(shift.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid shift ID"));
    }

}
