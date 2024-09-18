package AMS.ams.SERVICE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AMS.ams.ENTITY.Manager;
import AMS.ams.REPOSITORY.ManagerRepository;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;
    


    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public Optional<Manager> getManagerById(Long id) {
        return managerRepository.findById(id);
    }

    public Manager createManager(Manager manager) {
        return managerRepository.save(manager);
    }

    public Manager updateManager(Long id, Manager updatedManager) {
        Optional<Manager> existingManager = managerRepository.findById(id);
        if (existingManager.isPresent()) {
            Manager manager = existingManager.get();
            manager.setFirstName(updatedManager.getFirstName());
            manager.setLastName(updatedManager.getLastName());
            manager.setEmail(updatedManager.getEmail());
            if (updatedManager.getPassword() != null && !updatedManager.getPassword().isEmpty()) {
                manager.setPassword(updatedManager.getPassword());
            }
            return managerRepository.save(manager);
        } else {
            throw new RuntimeException("Manager not found with id " + id);
        }
    }


    public void deleteManager(Long id) {
        managerRepository.deleteById(id);
    }
}
