package com.quantity.service;

import com.quantity.model.QuantityHistory;
import com.quantity.repository.QuantityHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuantityHistoryService {

    @Autowired
    private QuantityHistoryRepository repository;

    public void save(String email, String operation, String details) {

        QuantityHistory history = new QuantityHistory();
        history.setEmail(email);
        history.setOperation(operation);
        history.setDetails(details);
        history.setCreatedAt(LocalDateTime.now());

        repository.save(history);
    }

    public List<QuantityHistory> getOperationHistory(String email, String operation) {
        return repository.findByEmailAndOperation(email, operation);
    }

    public List<QuantityHistory> getAllHistory(String email) {
        return repository.findByEmail(email);
    }
}