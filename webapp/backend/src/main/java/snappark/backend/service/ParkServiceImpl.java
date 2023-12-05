package snappark.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import snappark.backend.entity.Manager;
import snappark.backend.entity.OccupancyHistory;
import snappark.backend.entity.Park;
import snappark.backend.entity.User;
import snappark.backend.repository.ManagerRepository;
import snappark.backend.repository.OccupancyHistoryRepository;
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

    @Autowired(required = true)
    private OccupancyHistoryRepository occupancyHistoryRepository;
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
    public Park createPark(Park park, String username){        
        // TODO: Deal with situation where provided userId doesn't have a corresponding User
        
        Park savedPark = parkRepository.save(park);

        User user = userRepository.findUserByName(username);

        Manager manager = new Manager(user, savedPark);

        Manager savedManager = managerRepository.save(manager);
        return savedManager.getPark();
    }

    public Park updatePark(Long parkId){
        return parkRepository.findParkById(parkId);
    }

    public void deletePark(Long parkId){
        parkRepository.deleteById(parkId);
    }

    //
    // Manager entity operations
    //

    public List<Park> getParksByUsername(String username){
        List<Manager> managers = managerRepository.findByUserName(username);
        return managers.stream().map(Manager::getPark).collect(Collectors.toList());
    }

    public List<OccupancyHistory> getParkMovements(Long parkId){
        return occupancyHistoryRepository.findById_ParkId(parkId);
    }


    //
    // User entity operations
    //

    public User createUser(User user){
        return userRepository.save(user);
    }
}
