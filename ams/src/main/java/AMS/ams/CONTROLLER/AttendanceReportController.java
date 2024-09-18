package AMS.ams.CONTROLLER;

import AMS.ams.SERVICE.AttendanceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/attendanceReport")
public class AttendanceReportController {

    @Autowired
    private AttendanceReportService attendanceReportService;

    @GetMapping("/download/{employeeId}")
    public ResponseEntity<InputStreamResource> downloadAttendanceReport(@PathVariable("employeeId") Long employeeId) {

        ByteArrayInputStream bis = attendanceReportService.generateAndSaveAttendanceReportPdf(employeeId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=attendance_report_" + employeeId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
