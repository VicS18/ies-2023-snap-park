package snappark.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.AirQualityHistory;
import snappark.backend.entity.Park;
import snappark.backend.entity.Sensor;

public interface AirQualityHistoryRepository extends JpaRepository<AirQualityHistory, Long>{
    List<AirQualityHistory> findByDateBetweenAndParkAndSensorOrderByDateAsc(Long startDate, Long endDate, Park park, Sensor sensor);
}
