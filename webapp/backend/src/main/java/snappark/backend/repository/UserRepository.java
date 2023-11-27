package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long>{
   
    User findUserById(Long id);

    User findUserByName(String name);
}
