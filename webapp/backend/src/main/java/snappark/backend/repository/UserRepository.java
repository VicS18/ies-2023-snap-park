package snappark.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.User;


public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findUserById(Long id);

    User findUserByName(String name);
}
