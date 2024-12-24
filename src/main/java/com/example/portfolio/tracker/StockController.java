package com.example.portfolio.tracker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/stocks")

public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks(HttpServletRequest request) {
        String userId = getUserIdFromRequest(request);
        return new ResponseEntity<>(stockService.getStocksForUser(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Stock> addStock(@Validated @RequestBody Stock stock, HttpServletRequest request) {
        String userId = getUserIdFromRequest(request);
        return new ResponseEntity<>(stockService.addStock(userId, stock), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable String id, @Validated @RequestBody Stock stock, HttpServletRequest request) {
        String userId = getUserIdFromRequest(request);
        return new ResponseEntity<>(stockService.updateStock(userId, id, stock), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable String id, HttpServletRequest request) {
        String userId = getUserIdFromRequest(request);
        stockService.deleteStock(userId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/portfolio-value")
    public ResponseEntity<Double> getTotalPortfolioValue(HttpServletRequest request) {
        String userId = getUserIdFromRequest(request);
        return new ResponseEntity<>(stockService.calculateTotalPortfolioValue(userId), HttpStatus.OK);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<PortfolioMetrics> getPortfolioDashboard(HttpServletRequest request) {
        String userId = getUserIdFromRequest(request);
        PortfolioMetrics metrics = stockService.getPortfolioMetrics(userId);
        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }

    private String getUserIdFromRequest(HttpServletRequest request) {
        String userId = request.getHeader("X-User-Id");
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }
        return userId;
    }
    
    
}
