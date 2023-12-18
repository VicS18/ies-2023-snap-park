package snappark.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Park;
import snappark.backend.entity.Sensor;

public interface SensorRepository extends JpaRepository<Sensor,Long>{
    
    Integer countByPark(Park park);

    List<Sensor> findByPark(Park park);
}
