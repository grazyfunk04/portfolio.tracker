package com.example.portfolio.tracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock updateStock(String id, Stock stock) {
        stock.setId(id);
        return stockRepository.save(stock);
    }

    public void deleteStock(String id) {
        stockRepository.deleteById(id);
    }

    public double calculateTotalPortfolioValue() {
        return stockRepository.findAll().stream()
                .mapToDouble(stock -> stock.getQuantity() * stock.getBuyPrice())
                .sum();
    }

    public double fetchStockPrice(String ticker) {
        String apiUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + ticker + "&apikey=YOUR_API_KEY";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

        Map<String, String> quote = (Map<String, String>) response.get("Global Quote");
        return Double.parseDouble(quote.get("05. price"));
    }

    public PortfolioMetrics getPortfolioMetrics() {
        List<Stock> stocks = stockRepository.findAll();
        double totalValue = 0;
        Stock topPerformingStock = null;
        Map<String, Double> distribution = new HashMap<>();

        for (Stock stock : stocks) {
            double stockValue = stock.getQuantity() * stock.getBuyPrice();
            totalValue += stockValue;

            // Calculate top-performing stock based on some criteria (e.g., percentage increase)
            if (topPerformingStock == null || stockValue > (topPerformingStock.getQuantity() * topPerformingStock.getBuyPrice())) {
                topPerformingStock = stock;
            }

            
            distribution.put(stock.getTicker(), stockValue);
        }

        for (String ticker : distribution.keySet()) {
            double value = distribution.get(ticker);
            distribution.put(ticker, value / totalValue * 100);
        }

       
        PortfolioMetrics metrics = new PortfolioMetrics();
        metrics.setTotalValue(totalValue);
        metrics.setTopPerformingStock(topPerformingStock);
        metrics.setPortfolioDistribution(distribution);

        return metrics;
    }

}
