package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Alert;


public interface AlertRepository extends JpaRepository<Alert, Long>{
    
}
