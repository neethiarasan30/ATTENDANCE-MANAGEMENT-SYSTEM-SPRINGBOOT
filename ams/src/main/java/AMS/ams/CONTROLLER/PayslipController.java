package AMS.ams.CONTROLLER;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AMS.ams.DTO.PayslipRequestDTO;
import AMS.ams.ENTITY.Payslip;
import AMS.ams.SERVICE.PayslipService;

@RestController
@RequestMapping("/api/payslips")
public class PayslipController {

    @Autowired
    private PayslipService payslipService;

    @PostMapping("/generate")
    public Payslip generatePayslip(@RequestBody PayslipRequestDTO request) {
        return payslipService.generatePayslip(request.getEmployeeId(), request.getPayslipDate());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Payslip>> getPayslipsByEmployee(@PathVariable Long employeeId) {
        List<Payslip> payslips = payslipService.getPayslipsByEmployee(employeeId);
        return ResponseEntity.ok(payslips);
    }

}
