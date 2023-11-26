package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.AirQuality;

public interface AirQualityRepository extends JpaRepository<AirQuality,Long>{
    
}
