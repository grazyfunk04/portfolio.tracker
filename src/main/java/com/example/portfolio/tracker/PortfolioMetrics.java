package com.example.portfolio.tracker;

import java.util.Map;

public class PortfolioMetrics {
    private String userId;
    private double totalValue;
    private Stock topPerformingStock;
    private Map<String, Double> portfolioDistribution;

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public Stock getTopPerformingStock() {
        return topPerformingStock;
    }

    public void setTopPerformingStock(Stock topPerformingStock) {
        this.topPerformingStock = topPerformingStock;
    }

    public Map<String, Double> getPortfolioDistribution() {
        return portfolioDistribution;
    }

    public void setPortfolioDistribution(Map<String, Double> portfolioDistribution) {
        this.portfolioDistribution = portfolioDistribution;
    }
}
