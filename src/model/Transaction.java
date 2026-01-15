package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Clasa = tranzactie efectuata.

public class Transaction {
    private String assetSymbol;
    private String assetName;
    private double quantity;
    private double price;
    private String type;
    private LocalDateTime date;

    public Transaction(String assetSymbol, String assetName, double quantity,
                       double price, String type) {
        this.assetSymbol = assetSymbol;
        this.assetName = assetName;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.date = LocalDateTime.now();
    }

    public String getAssetSymbol() {
        return assetSymbol;
    }

    public String getAssetName() {
        return assetName;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return String.format("[%s] %s - %s: %.2f x %.2f = %.2f RON",
                date.format(formatter), type, assetName, quantity, price, quantity * price);
    }
}