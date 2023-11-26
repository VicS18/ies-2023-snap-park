package ies.snapPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.snapPark.entity.AirQuality;

public interface AirQualityRepository extends JpaRepository<AirQuality,Long>{
    
}
