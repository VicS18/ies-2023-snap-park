package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long>{
    
}
