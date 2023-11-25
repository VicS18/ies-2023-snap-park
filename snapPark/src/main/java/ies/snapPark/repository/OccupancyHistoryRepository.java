package ies.snapPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.snapPark.entity.OccupancyHistory;

public interface OccupancyHistoryRepository extends JpaRepository<OccupancyHistory,Long>{
    
}
