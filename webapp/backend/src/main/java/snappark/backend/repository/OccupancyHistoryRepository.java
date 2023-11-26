package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.OccupancyHistory;
import snappark.backend.entity.OccupancyHistory.OccupancyHistoryId;;

public interface OccupancyHistoryRepository extends JpaRepository<OccupancyHistory,OccupancyHistoryId>{
    
}
