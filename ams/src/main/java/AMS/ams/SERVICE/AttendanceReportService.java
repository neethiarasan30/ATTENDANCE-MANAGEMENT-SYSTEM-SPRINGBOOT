package AMS.ams.SERVICE;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import AMS.ams.ENTITY.Attendance;
import AMS.ams.REPOSITORY.AttendanceRepository;

@Service
public class AttendanceReportService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public ByteArrayInputStream generateAndSaveAttendanceReportPdf(Long employeeId) {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        
        List<Attendance> attendanceList = attendanceRepository.findByEmployeeIdAndDateBetween(
                employeeId, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
            // Create PDF writer
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Adding the header
            document.add(new Paragraph("Attendance Report")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(18));
            document.add(new Paragraph("Employee ID: " + employeeId)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(12));

            // Adding the date range
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            document.add(new Paragraph("Date Range: " + startDate.format(formatter) + " to " + endDate.format(formatter))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(12));

            document.add(new Paragraph("\n"));

            // Create table for attendance
            Table table = new Table(3);
            table.addHeaderCell("Date");
            table.addHeaderCell("Check-In Time");
            table.addHeaderCell("Check-Out Time");

            // Populate table with attendance data
            for (Attendance attendance : attendanceList) {
                table.addCell(attendance.getDate().toLocalDate().toString());
                table.addCell(attendance.getCheckInTime() != null ? attendance.getCheckInTime().toString() : "N/A");
                table.addCell(attendance.getCheckOutTime() != null ? attendance.getCheckOutTime().toString() : "N/A");
            }

            // Add table to the document
            document.add(table);

            // Close document
            document.close();

            // Save PDF to local memory
            Path path = Paths.get("attendance_report_" + employeeId + ".pdf");
            Files.write(path, out.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
