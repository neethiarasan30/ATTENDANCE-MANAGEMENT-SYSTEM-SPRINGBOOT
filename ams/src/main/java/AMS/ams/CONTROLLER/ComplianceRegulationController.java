package AMS.ams.CONTROLLER;

import AMS.ams.ENTITY.ComplianceRegulation;
import AMS.ams.SERVICE.ComplianceRegulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance-regulations")
public class ComplianceRegulationController {
    @Autowired
    private ComplianceRegulationService complianceRegulationService;

    @PostMapping
    public ComplianceRegulation createRegulation(@RequestBody ComplianceRegulation regulation) {
        return complianceRegulationService.createRegulation(regulation);
    }

    @GetMapping
    public List<ComplianceRegulation> getAllRegulations() {
        return complianceRegulationService.getAllRegulations();
    }

    @GetMapping("/{id}")
    public ComplianceRegulation getRegulationById(@PathVariable Long id) {
        return complianceRegulationService.getRegulationById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteRegulation(@PathVariable Long id) {
        complianceRegulationService.deleteRegulation(id);
    }
}
