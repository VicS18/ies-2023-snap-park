package ies.snapPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.snapPark.entity.Park;

public interface ParkRepository extends JpaRepository<Park,Long>{
    
}
