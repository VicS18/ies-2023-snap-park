package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.OccupancyHistory;

public interface OccupancyHistoryRepository extends JpaRepository<OccupancyHistory,Long>{
    
}
