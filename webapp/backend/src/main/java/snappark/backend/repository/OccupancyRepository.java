package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Occupancy;
import snappark.backend.entity.Park;

public interface OccupancyRepository extends JpaRepository<Occupancy, Park>{
    
}
