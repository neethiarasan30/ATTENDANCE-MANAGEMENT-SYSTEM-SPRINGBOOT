package AMS.ams.CONTROLLER;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AMS.ams.SERVICE.PayslipReportService;

@RestController
@RequestMapping("/api/payslipReports")
public class PayslipReportController {

    @Autowired
    private PayslipReportService payslipReportService;

    @GetMapping("/generate/{employeeId}")
    public ResponseEntity<Resource> generatePayslipReport(@PathVariable Long employeeId) {
        String filePath = "payslip_" + employeeId + ".pdf";
        try {
            // Generate the payslip report and save it as a PDF
            payslipReportService.generatePayslipReport(employeeId, filePath);

            // Create a file resource from the file path
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            InputStreamResource resource = new InputStreamResource(fis);

            // Prepare the HTTP response
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePath);
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
