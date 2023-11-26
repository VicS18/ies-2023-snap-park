package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.TemperatureHistory;

public interface TemperatureHistoryRepository extends JpaRepository<TemperatureHistory,Long>{
    
}
