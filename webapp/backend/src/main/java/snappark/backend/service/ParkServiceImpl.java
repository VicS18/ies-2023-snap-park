package snappark.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import snappark.backend.entity.Manager;
import snappark.backend.entity.Park;
import snappark.backend.repository.ManagerRepository;
import snappark.backend.repository.ParkRepository;

@Service
@AllArgsConstructor
public class ParkServiceImpl implements ParkService {

    private ParkRepository parkRepository;
    private ManagerRepository managerRepository;

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
    public Park createPark(Park park){
        return parkRepository.save(park);
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

    
}
