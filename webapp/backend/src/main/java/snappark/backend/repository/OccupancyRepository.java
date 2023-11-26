package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Occupancy;

public interface OccupancyRepository extends JpaRepository<Occupancy,Long>{
    
}
