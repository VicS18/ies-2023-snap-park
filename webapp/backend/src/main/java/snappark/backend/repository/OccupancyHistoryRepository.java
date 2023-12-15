package snappark.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.OccupancyHistory;
import snappark.backend.entity.OccupancyHistory.OccupancyHistoryId;;

public interface OccupancyHistoryRepository extends JpaRepository<OccupancyHistory,OccupancyHistoryId>{
    List<OccupancyHistory> findById_ParkId(Long parkId);
    List<OccupancyHistory> findByDateBetweenAndId_Park_IdOrderByDateAsc(Long parkId, Long startDate, Long endDate);
}