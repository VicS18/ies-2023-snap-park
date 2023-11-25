package ies.snapPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.snapPark.entity.TemperatureHistory;

public interface TemperatureHistoryRepository extends JpaRepository<TemperatureHistory,Long>{
    
}
