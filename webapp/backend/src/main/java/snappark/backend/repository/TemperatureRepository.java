package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Temperature;
import snappark.backend.entity.Temperature.TemperatureId;;

public interface TemperatureRepository extends JpaRepository<Temperature,TemperatureId>{
    
}
