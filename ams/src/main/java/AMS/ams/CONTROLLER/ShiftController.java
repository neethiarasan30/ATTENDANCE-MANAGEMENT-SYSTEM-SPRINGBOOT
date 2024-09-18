package AMS.ams.CONTROLLER;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import AMS.ams.ENTITY.Shift;
import AMS.ams.SERVICE.ShiftService;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @GetMapping("/getAllShifts")
    public List<Shift> getAllShifts() {
        return shiftService.getAllShifts();
    }

    @GetMapping("/getShiftById/{id}")
    public ResponseEntity<Shift> getShiftById(@PathVariable Long id) {
        Optional<Shift> shift = shiftService.getShiftById(id);
        return shift.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/postShift")
    public Shift createShift(@RequestBody Shift shift) {
        return shiftService.saveShift(shift);
    }

    @PutMapping("/updateShift/{id}")
    public ResponseEntity<Shift> updateShift(@PathVariable Long id, @RequestBody Shift shiftDetails) {
        Shift updatedShift = shiftService.updateShift(id, shiftDetails);
        if (updatedShift != null) {
            return ResponseEntity.ok(updatedShift);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteShift/{id}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long id) {
        boolean isDeleted = shiftService.deleteShift(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
