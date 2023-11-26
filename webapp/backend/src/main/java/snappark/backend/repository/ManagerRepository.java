package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Manager;
import snappark.backend.entity.Manager.ManagerId;;

public interface ManagerRepository extends JpaRepository<Manager,ManagerId>{
    
}
