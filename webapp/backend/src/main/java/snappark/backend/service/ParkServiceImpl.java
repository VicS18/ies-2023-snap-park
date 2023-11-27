package snappark.backend.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import snappark.backend.entity.Park;
import snappark.backend.repository.ParkRepository;

@Service
@AllArgsConstructor
public class ParkServiceImpl implements ParkService {

    private ParkRepository parkRepository;
    
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
    // User entity operations
    //

    
}
