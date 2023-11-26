package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    
}
