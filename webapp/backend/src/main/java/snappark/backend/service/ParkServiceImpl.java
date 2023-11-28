package snappark.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import snappark.backend.entity.Manager;
import snappark.backend.entity.Manager.ManagerId;
import snappark.backend.entity.Park;
import snappark.backend.entity.User;
import snappark.backend.repository.ManagerRepository;
import snappark.backend.repository.ParkRepository;
import snappark.backend.repository.UserRepository;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ParkServiceImpl implements ParkService {

    @Autowired(required = true)
    private ParkRepository parkRepository;
    @Autowired(required = true)
    private ManagerRepository managerRepository;
    @Autowired(required = true)
    private UserRepository userRepository;

    //
    // Park entity operations
    // 

    public Park getParkById(Long id){
        return parkRepository.findParkById(id);
    }
    
    public Park getParkByName(String name){
        return parkRepository.findParkByName(name);    
    }

    // Shouldn't receive Park object argument
    public Park createPark(Park park, Long userId){        
        // TODO: Deal with situation where provided userId doesn't have a corresponding User
        
        System.out.println("====== PARK: " + park);

        User user = userRepository.findUserById(userId);
        Park savedPark = parkRepository.save(park);
        System.out.println("========= USER: " + user);

        Manager.ManagerId managerId = new Manager.ManagerId(user, savedPark);

        Manager manager = new Manager(managerId, user, savedPark);
        user.getManagers().add(manager);
        savedPark.getManagers().add(manager);

        System.out.println("ManagerId: " + manager.getId());
        System.out.println("User: " + manager.getUser());
        System.out.println("Park: " + manager.getPark());

        System.out.println("======= MANAGER: " + manager);

        Manager savedManager = managerRepository.save(manager);
        System.out.println("=========== SAVED MANAGER: " + savedManager);
        return savedManager.getPark();
    }

    //
    // Manager entity operations
    //

    public List<Park> getParksByUserId(Long id){
        List<Manager> managers = managerRepository.findByUserId(id);
        return managers.stream().map(Manager::getPark).collect(Collectors.toList());
    }


    //
    // User entity operations
    //

    public User createUser(User user){
        return userRepository.save(user);
    }
}
