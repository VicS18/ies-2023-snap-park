package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.AirQuality;
import snappark.backend.entity.AirQuality.AirQualityId;;


public interface AirQualityRepository extends JpaRepository<AirQuality,AirQualityId>{
    
}
