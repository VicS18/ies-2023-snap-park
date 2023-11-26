package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Light;

public interface LightRepository extends JpaRepository<Light,Long>{
    
}
