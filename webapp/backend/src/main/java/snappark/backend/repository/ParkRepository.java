package snappark.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Park;

public interface ParkRepository extends JpaRepository<Park,Long>{
    Park findParkById(Long id);
    
    Park findParkByName(String name); 
}
