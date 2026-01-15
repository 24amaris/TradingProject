package model;

//actiune / criptomoneda
public class Asset {
    private String name;
    private String symbol;
    private double price;
    private double quantity;
    private boolean isCrypto;

    public Asset(String name, String symbol, double price, double quantity, boolean isCrypto) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.isCrypto = isCrypto;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public boolean isCrypto() {
        return isCrypto;
    }

    @Override
    public String toString() {
        String type = isCrypto ? "CRYPTO" : "STOCK";
        return String.format("[%s] %s (%s) - Pret: %.2f, Disponibil: %.2f",
                type, name, symbol, price, quantity);
    }
}