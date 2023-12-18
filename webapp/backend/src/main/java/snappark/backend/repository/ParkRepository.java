package snappark.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Park;

public interface ParkRepository extends JpaRepository<Park,Long>{
    Park findParkById(Long id);
    
    Optional<Park> findParkByName(String name); 
}
