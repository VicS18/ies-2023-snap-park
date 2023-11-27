package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.AirQualityHistory;
import snappark.backend.entity.AirQualityHistory.AirQualityHistoryId;

public interface AirQualityHistoryRepository extends JpaRepository<AirQualityHistory, AirQualityHistoryId>{
    
}
