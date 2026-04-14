package com.quantity.repository;

import com.quantity.model.QuantityHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuantityHistoryRepository extends JpaRepository<QuantityHistory, Long> {

    List<QuantityHistory> findByEmail(String email);

    List<QuantityHistory> findByEmailAndOperation(String email, String operation);
}