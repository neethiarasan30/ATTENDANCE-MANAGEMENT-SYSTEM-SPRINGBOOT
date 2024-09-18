package AMS.ams.CONTROLLER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AMS.ams.ENTITY.Designation;
import AMS.ams.SERVICE.DesignationService;

@RestController
@RequestMapping("/api/designations")
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    @GetMapping("/getAllDesignation")
    public List<Designation> getAllDesignations() {
        return designationService.getAllDesignations();
    }

    @PostMapping("/PostDesignation")
    public Designation createDesignation(@RequestBody Designation designation) {
        return designationService.saveDesignation(designation);
    }

    // Update Designation
    @PutMapping("/updateDesignation/{id}")
    public Designation updateDesignation(@PathVariable Long id, @RequestBody Designation designationDetails) {
        return designationService.updateDesignation(id, designationDetails);
    }

    // Delete Designation
    @DeleteMapping("/deleteDesignation/{id}")
    public void deleteDesignation(@PathVariable Long id) {
        designationService.deleteDesignation(id);
    }
}
