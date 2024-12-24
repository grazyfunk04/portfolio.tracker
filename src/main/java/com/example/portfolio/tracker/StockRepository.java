package com.example.portfolio.tracker;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockRepository extends MongoRepository<Stock, String> {
    List<Stock> findByUserId(String userId);
}
