package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Park;

public interface ParkRepository extends JpaRepository<Park,Long>{
    
}
