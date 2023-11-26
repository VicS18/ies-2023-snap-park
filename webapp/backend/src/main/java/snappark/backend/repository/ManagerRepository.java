package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager,Long>{
    
}
