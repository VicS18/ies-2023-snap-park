package ies.snapPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.snapPark.entity.Sensor;

public interface SensorRepository extends JpaRepository<Sensor,Long>{
    
}
