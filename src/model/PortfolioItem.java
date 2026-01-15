package model;
import java.time.LocalDate;

public class PortfolioItem {
    private Asset asset;
    private double quantity;
    private LocalDate purchaseDate;

    public PortfolioItem(Asset asset, double quantity) {
        this.asset = asset;
        this.quantity = quantity;
        this.purchaseDate = LocalDate.now();
    }

    public Asset getAsset() {
        return asset;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate date) {
        this.purchaseDate = date;
    }

    public void addQuantity(double amount) {
        this.quantity += amount;
        this.purchaseDate = LocalDate.now();
    }

    @Override
    public String toString() {
        double value = quantity * asset.getPrice();
        return String.format("%s (%s): %.2f unitati, Valoare: %.2f RON",
                asset.getName(), asset.getSymbol(), quantity, value);
    }
}