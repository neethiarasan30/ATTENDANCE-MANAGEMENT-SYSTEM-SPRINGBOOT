package AMS.ams.SERVICE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AMS.ams.ENTITY.Attendance;
import AMS.ams.ENTITY.Employee;
import AMS.ams.REPOSITORY.AttendanceRepository;
import AMS.ams.REPOSITORY.EmployeeRepository;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Attendance checkIn(Long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            LocalDate today = LocalDate.now();
            LocalDateTime startOfDay = today.atStartOfDay();
            LocalDateTime endOfDay = today.atTime(23, 59, 59);

            List<Attendance> attendanceList = attendanceRepository.findByEmployeeIdAndDateBetween(employeeId, startOfDay, endOfDay);

            if (!attendanceList.isEmpty() && attendanceList.get(0).getCheckOutTime() == null) {
                throw new IllegalStateException("Already checked in today. Please check out first.");
            }

            Attendance attendance = new Attendance();
            attendance.setEmployee(employee);
            attendance.setCheckInTime(LocalDateTime.now());
            attendance.setDate(today.atStartOfDay());
            attendance.setStatus("Marked");

            return attendanceRepository.save(attendance);
        } else {
            throw new IllegalArgumentException("Employee not found");
        }
    }

    public Attendance checkOut(Long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            LocalDate today = LocalDate.now();
            LocalDateTime startOfDay = today.atStartOfDay();
            LocalDateTime endOfDay = today.atTime(23, 59, 59);

            List<Attendance> attendanceList = attendanceRepository.findByEmployeeIdAndDateBetween(employeeId, startOfDay, endOfDay);

            if (attendanceList.isEmpty()) {
                throw new IllegalStateException("No check-in record found for today.");
            }

            Attendance attendance = attendanceList.get(0);
            if (attendance.getCheckOutTime() != null) {
                throw new IllegalStateException("Already checked out today.");
            }

            attendance.setCheckOutTime(LocalDateTime.now());
            attendance.setWorkedHours(attendance.calculateWorkedHours(attendance.getCheckInTime(), attendance.getCheckOutTime()));
            attendance.setStatus("Marked");

            return attendanceRepository.save(attendance);
        } else {
            throw new IllegalArgumentException("Employee not found");
        }
    }

    // **View Attendance for a Specific Employee**
    public List<Attendance> viewAttendanceByEmployee(Long employeeId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.atTime(23, 59, 59);
        return attendanceRepository.findByEmployeeIdAndDateBetween(employeeId, startOfDay, endOfDay);
    }
}
