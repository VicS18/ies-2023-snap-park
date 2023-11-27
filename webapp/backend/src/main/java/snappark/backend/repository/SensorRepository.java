package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Sensor;

public interface SensorRepository extends JpaRepository<Sensor,Long>{
    
}
