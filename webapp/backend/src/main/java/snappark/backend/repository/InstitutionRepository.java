package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Institution;

public interface InstitutionRepository extends JpaRepository<Institution,Long>{
    
}
