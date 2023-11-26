package ies.snapPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.snapPark.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    
}
