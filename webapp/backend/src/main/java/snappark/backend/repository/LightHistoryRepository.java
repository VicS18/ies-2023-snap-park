package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.LightHistory;
import snappark.backend.entity.LightHistory.LightHistoryId;;

public interface LightHistoryRepository extends JpaRepository<LightHistory,LightHistoryId>{
    
}
