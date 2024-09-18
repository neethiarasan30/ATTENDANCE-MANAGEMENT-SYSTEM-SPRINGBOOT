package AMS.ams.SERVICE;

import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import AMS.ams.ENTITY.Payslip;
import AMS.ams.EXCEPTION.ResourceNotFoundException;
import AMS.ams.REPOSITORY.PayslipRepository;

@Service
public class PayslipReportService {

    @Autowired
    private PayslipRepository payslipRepository;

    public void generatePayslipReport(Long employeeId, String filePath) throws IOException {
        Payslip payslip = payslipRepository.findByEmployeeId(employeeId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Payslip not found for employee ID: " + employeeId));

        PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Payslip Report"));
        document.add(new Paragraph("Employee ID: " + payslip.getEmployee().getId()));
        document.add(new Paragraph("Employee Name: " + payslip.getEmployee().getFirstName() + " " + payslip.getEmployee().getLastName()));
        document.add(new Paragraph("Payslip Date: " + payslip.getPayslipDate()));
        document.add(new Paragraph("Basic Salary: " + payslip.getBasicSalary()));
        document.add(new Paragraph("Allowances: " + payslip.getAllowances()));
        document.add(new Paragraph("Deductions: " + payslip.getDeductions()));
        document.add(new Paragraph("Net Pay: " + payslip.getNetPay()));

        document.close();
    }
}
