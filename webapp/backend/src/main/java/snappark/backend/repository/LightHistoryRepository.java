package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.LightHistory;

public interface LightHistoryRepository extends JpaRepository<LightHistory,Long>{
    
}
