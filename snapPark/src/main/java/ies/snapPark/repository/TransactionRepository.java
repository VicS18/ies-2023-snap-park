package ies.snapPark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.snapPark.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long>{
    
}
