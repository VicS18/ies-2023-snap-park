package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.TemperatureHistory;
import snappark.backend.entity.TemperatureHistory.TemperatureHistoryId;

public interface TemperatureHistoryRepository extends JpaRepository<TemperatureHistory,TemperatureHistoryId>{
    
}
