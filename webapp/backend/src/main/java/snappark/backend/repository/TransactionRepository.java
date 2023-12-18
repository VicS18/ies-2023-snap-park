package snappark.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Park;
import snappark.backend.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long>{

    List<Transaction> findByDateBetweenAndParkOrderByDateAsc(Long startDate, Long finishDate, Park park);
}
