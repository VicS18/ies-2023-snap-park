package ies.snapPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.snapPark.entity.LightHistory;

public interface LightHistoryRepository extends JpaRepository<LightHistory,Long>{
    
}
