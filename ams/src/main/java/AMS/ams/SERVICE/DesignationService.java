package AMS.ams.SERVICE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AMS.ams.ENTITY.Designation;
import AMS.ams.REPOSITORY.DesignationRepository;

@Service
public class DesignationService {

    @Autowired
    private DesignationRepository designationRepository;

    public List<Designation> getAllDesignations() {
        return designationRepository.findAll();
    }

    public Designation saveDesignation(Designation designation) {
        return designationRepository.save(designation);
    }

    // Update Designation
    public Designation updateDesignation(Long id, Designation designationDetails) {
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Designation not found with id: " + id));
        
        // Updating the title
        designation.setTitle(designationDetails.getTitle());

        return designationRepository.save(designation);
    }

    // Delete Designation
    public void deleteDesignation(Long id) {
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Designation not found with id: " + id));
        designationRepository.delete(designation);
    }
}
