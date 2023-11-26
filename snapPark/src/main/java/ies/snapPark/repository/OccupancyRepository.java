package ies.snapPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.snapPark.entity.Occupancy;

public interface OccupancyRepository extends JpaRepository<Occupancy,Long>{
    
}
