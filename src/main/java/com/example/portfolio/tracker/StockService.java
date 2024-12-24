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

    @Autowired
    private UserRepository userRepository;

    public List<Stock> getStocksForUser(String userId) {
        return stockRepository.findByUserId(userId);
    }

    public Stock addStock(String userId, Stock stock) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        stock.setUser(user);
        return stockRepository.save(stock);
    }

    public Stock updateStock(String userId, String stockId, Stock stock) {
        Stock existingStock = stockRepository.findById(stockId).orElseThrow(() -> new RuntimeException("Stock not found"));
        if (!existingStock.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to stock");
        }
        existingStock.setName(stock.getName());
        existingStock.setQuantity(stock.getQuantity());
        return stockRepository.save(existingStock);
    }

    public void deleteStock(String userId, String stockId) {
        Stock existingStock = stockRepository.findById(stockId).orElseThrow(() -> new RuntimeException("Stock not found"));
        if (!existingStock.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to stock");
        }
        stockRepository.delete(existingStock);
    }

    public double calculateTotalPortfolioValue(String userId) {
    List<Stock> stocks = getStocksForUser(userId);
    double totalValue = 0;

    for (Stock stock : stocks) {
        double stockPrice;
        try {
            stockPrice = fetchStockPrice(stock.getTicker());
        } catch (Exception e) {
            stockPrice = stock.getBuyPrice();
        }
        totalValue += stock.getQuantity() * stockPrice;
    }
    return totalValue;
}


    public double fetchStockPrice(String ticker) {
        String apiUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + ticker + "&apikey=YOUR_API_KEY";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

        Map<String, String> quote = (Map<String, String>) response.get("Global Quote");
        return Double.parseDouble(quote.get("05. price"));
    }

    public PortfolioMetrics getPortfolioMetrics(String userId) {
        List<Stock> stocks = getStocksForUser(userId);
        double totalValue = 0;
        Stock topPerformingStock = null;
        Map<String, Double> distribution = new HashMap<>();

        for (Stock stock : stocks) {
            double stockValue = stock.getQuantity() * stock.getBuyPrice();
            totalValue += stockValue;

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
