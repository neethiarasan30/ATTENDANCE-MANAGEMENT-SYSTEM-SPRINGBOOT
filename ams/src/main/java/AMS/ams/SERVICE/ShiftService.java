package AMS.ams.SERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AMS.ams.ENTITY.Shift;
import AMS.ams.REPOSITORY.ShiftRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    public Optional<Shift> getShiftById(Long id) {
        return shiftRepository.findById(id);
    }

    public Shift saveShift(Shift shift) {
        return shiftRepository.save(shift);
    }

    public Shift updateShift(Long id, Shift shiftDetails) {
        Optional<Shift> shift = shiftRepository.findById(id);

        if (shift.isPresent()) {
            Shift existingShift = shift.get();
            existingShift.setShiftName(shiftDetails.getShiftName());
            existingShift.setStartTime(shiftDetails.getStartTime());
            existingShift.setEndTime(shiftDetails.getEndTime());
            return shiftRepository.save(existingShift);
        } else {
            return null;
        }
    }

    public boolean deleteShift(Long id) {
        if (shiftRepository.existsById(id)) {
            shiftRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
