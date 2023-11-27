package snappark.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Manager;
import snappark.backend.entity.User;
import snappark.backend.entity.Manager.ManagerId;

public interface ManagerRepository extends JpaRepository<Manager,ManagerId>{

    List<Manager> findByUserId(Long id);
}
