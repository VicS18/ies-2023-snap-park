package snappark.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import snappark.backend.entity.Manager;
import snappark.backend.entity.User;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long>{

    List<Manager> findByUser(User user);

}
