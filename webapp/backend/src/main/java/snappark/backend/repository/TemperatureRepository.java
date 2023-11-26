package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Temperature;

public interface TemperatureRepository extends JpaRepository<Temperature,Long>{
    
}
