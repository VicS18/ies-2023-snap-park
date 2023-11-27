package snappark.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import snappark.backend.entity.Manager;
import snappark.backend.entity.Manager.ManagerId;
import snappark.backend.entity.Park;
import snappark.backend.entity.User;
import snappark.backend.repository.ManagerRepository;
import snappark.backend.repository.ParkRepository;
import snappark.backend.repository.UserRepository;

@Service
@AllArgsConstructor
public class ParkServiceImpl implements ParkService {

    private ParkRepository parkRepository;
    private ManagerRepository managerRepository;
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
 
        User user = userRepository.findUserById(userId);
        Manager manager = new Manager(new ManagerId(user, park), user, park);
        return managerRepository.save(manager).getPark();
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
