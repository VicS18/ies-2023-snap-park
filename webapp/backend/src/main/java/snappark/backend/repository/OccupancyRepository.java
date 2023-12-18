package snappark.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Occupancy;

public interface OccupancyRepository extends JpaRepository<Occupancy, Long>{
    Optional<Occupancy> findByParkId(Long id);
}
