package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String username;
    private String password;
    private double balance;

    private Map<String, PortfolioItem> portfolio;

    private List<Transaction> transactionHistory;

    private List<String> watchList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 10000.0;
        this.portfolio = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
        this.watchList = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Map<String, PortfolioItem> getPortfolio() {
        return portfolio;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public List<String> getWatchList() {
        return watchList;
    }

    public void addToWatchList(String symbol) {
        if (!watchList.contains(symbol)) {
            watchList.add(symbol);
        }
    }

    public void removeFromWatchList(String symbol) {
        watchList.remove(symbol);
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public void addToPortfolio(Asset asset, double quantity) {
        String symbol = asset.getSymbol();
        if (portfolio.containsKey(symbol)) {
            portfolio.get(symbol).addQuantity(quantity);
        } else {
            portfolio.put(symbol, new PortfolioItem(asset, quantity));
        }
    }

    public boolean removeFromPortfolio(String symbol, double quantity) {
        if (!portfolio.containsKey(symbol)) {
            return false;
        }
        PortfolioItem item = portfolio.get(symbol);
        if (item.getQuantity() < quantity) {
            return false;
        }
        item.setQuantity(item.getQuantity() - quantity);
        if (item.getQuantity() <= 0) {
            portfolio.remove(symbol);
        }
        return true;
    }
}