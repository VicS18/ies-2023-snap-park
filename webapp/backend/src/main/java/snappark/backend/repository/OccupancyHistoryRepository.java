package snappark.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.OccupancyHistory;
import snappark.backend.entity.Park;

public interface OccupancyHistoryRepository extends JpaRepository<OccupancyHistory,Long>{
    List<OccupancyHistory> findByPark(Park park);
    List<OccupancyHistory> findByDateBetweenAndParkOrderByDateAsc(Long startDate, Long endDate, Park park);
}