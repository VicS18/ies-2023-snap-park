package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.AirQualityHistory;

public interface AirQualityHistoryRepository extends JpaRepository<AirQualityHistory,Long>{
    
}
