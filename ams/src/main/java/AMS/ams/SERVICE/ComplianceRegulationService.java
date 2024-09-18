package AMS.ams.SERVICE;

import AMS.ams.ENTITY.ComplianceRegulation;
import AMS.ams.REPOSITORY.ComplianceRegulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceRegulationService {
    @Autowired
    private ComplianceRegulationRepository complianceRegulationRepository;

    public ComplianceRegulation createRegulation(ComplianceRegulation regulation) {
        return complianceRegulationRepository.save(regulation);
    }

    public List<ComplianceRegulation> getAllRegulations() {
        return complianceRegulationRepository.findAll();
    }

    public ComplianceRegulation getRegulationById(Long id) {
        return complianceRegulationRepository.findById(id).orElse(null);
    }

    public void deleteRegulation(Long id) {
        complianceRegulationRepository.deleteById(id);
    }
}
