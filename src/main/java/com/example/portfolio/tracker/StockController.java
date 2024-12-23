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

@RestController
@RequestMapping("/api/stocks")

public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        return new ResponseEntity<>(stockService.getAllStocks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Stock> addStock(@Validated @RequestBody Stock stock) {
        return new ResponseEntity<>(stockService.addStock(stock), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable String id, @Validated @RequestBody Stock stock) {
        return new ResponseEntity<>(stockService.updateStock(id, stock), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable String id) {
        stockService.deleteStock(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/portfolio-value")
    public ResponseEntity<Double> getTotalPortfolioValue() {
        return new ResponseEntity<>(stockService.calculateTotalPortfolioValue(), HttpStatus.OK);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<PortfolioMetrics> getPortfolioDashboard() {
        PortfolioMetrics metrics = stockService.getPortfolioMetrics();
        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }
}
