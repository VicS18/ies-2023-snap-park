package snappark.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import snappark.backend.entity.Manager;
import snappark.backend.entity.ManagerId;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,ManagerId>{

    List<Manager> findByUserId(Long id);

    List<Manager> findByUserName(String name);
}
