package ies.snapPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.snapPark.entity.AirQualityHistory;

public interface AirQualityHistoryRepository extends JpaRepository<AirQualityHistory,Long>{
    
}
