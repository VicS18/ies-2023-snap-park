package ies.snapPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.snapPark.entity.Institution;

public interface InstitutionRepository extends JpaRepository<Institution,Long>{
    
}
