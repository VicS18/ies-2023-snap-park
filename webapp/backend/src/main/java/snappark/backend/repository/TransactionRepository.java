package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import snappark.backend.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long>{
    
    @Query(value = "SELECT SUM(profit) FROM Transaction WHERE entrance_park_id = ? AND timestamp >= ? AND timestamp < ?", nativeQuery = true)
    Double sumByParkIdTime(Long parkId, Long currYear, Long nextYear);
}
