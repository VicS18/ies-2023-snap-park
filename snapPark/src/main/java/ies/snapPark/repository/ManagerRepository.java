package ies.snapPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.snapPark.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager,Long>{
    
}
