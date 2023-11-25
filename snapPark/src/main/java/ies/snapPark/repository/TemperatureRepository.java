package ies.snapPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.snapPark.entity.Temperature;

public interface TemperatureRepository extends JpaRepository<Temperature,Long>{
    
}
