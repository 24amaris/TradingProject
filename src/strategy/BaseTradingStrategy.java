package strategy;

import model.Asset;
import model.PortfolioItem;
import model.Transaction;
import model.User;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * PATTERN: TEMPLATE METHOD
 *
 * Aceasta clasa abstracta defineste "scheletul" unei tranzactii.
 * Pasii comuni sunt implementati aici, iar pasii specifici sunt
 * lasati pentru subclase (strategiile concrete).
 *
 * Template Method = defineste algoritmul in clasa de baza,
 * dar lasa anumiti pasi sa fie implementati de subclase.
 */
public abstract class BaseTradingStrategy implements TradingStrategy {

    /**
     * TEMPLATE METHOD pentru cumparare
     * Defineste pasii: validare -> verificare restrictii -> executare
     */
    @Override
    public final boolean buy(User user, Asset asset, double quantity) {
        if (!validateBasicBuy(user, asset, quantity)) {
            return false;
        }

        if (!canBuy(user, asset, quantity)) {
            return false;
        }

        return executeBuy(user, asset, quantity);
    }

    /**
     * TEMPLATE METHOD pt vanzare
     */
    @Override
    public final boolean sell(User user, Asset asset, double quantity) {

        if (!validateBasicSell(user, asset, quantity)) {
            return false;
        }

        if (!canSell(user, asset, quantity)) {
            return false;
        }

        return executeSell(user, asset, quantity);
    }

    protected boolean validateBasicBuy(User user, Asset asset, double quantity) {
        double totalCost = asset.getPrice() * quantity;

        if (user.getBalance() < totalCost) {
            System.out.println("Sold insuficient! Ai: " + user.getBalance() +
                    " RON, Necesar: " + totalCost + " RON");
            return false;
        }

        if (asset.getQuantity() < quantity) {
            System.out.println("Cantitate indisponibila pe piata!");
            return false;
        }

        return true;
    }

    protected boolean validateBasicSell(User user, Asset asset, double quantity) {
        PortfolioItem item = user.getPortfolio().get(asset.getSymbol());

        if (item == null || item.getQuantity() < quantity) {
            System.out.println("Nu detii suficiente unitati din acest activ!");
            return false;
        }

        return true;
    }

    protected boolean isStockTradingAllowed(Asset asset) {
        if (asset.isCrypto()) {
            return true;
        }

        LocalDateTime now = LocalDateTime.now();
        DayOfWeek day = now.getDayOfWeek();
        LocalTime time = now.toLocalTime();

        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            System.out.println("Actiunile pot fi tranzactionate doar Luni-Vineri!");
            return false;
        }

        LocalTime marketOpen = LocalTime.of(9, 0);
        LocalTime marketClose = LocalTime.of(18, 0);

        if (time.isBefore(marketOpen) || time.isAfter(marketClose)) {
            System.out.println("Actiunile pot fi tranzactionate doar intre 09:00-18:00!");
            return false;
        }

        return true;
    }


    protected boolean executeBuy(User user, Asset asset, double quantity) {
        double totalCost = asset.getPrice() * quantity;

        user.setBalance(user.getBalance() - totalCost);

        user.addToPortfolio(asset, quantity);

        asset.setQuantity(asset.getQuantity() - quantity);

        Transaction transaction = new Transaction(
                asset.getSymbol(), asset.getName(), quantity, asset.getPrice(), "BUY");
        user.addTransaction(transaction);

        System.out.printf("Cumparare reusita: %.2f x %s la %.2f RON = %.2f RON\n",
                quantity, asset.getSymbol(), asset.getPrice(), totalCost);

        return true;
    }

    protected boolean executeSell(User user, Asset asset, double quantity) {
        double totalValue = asset.getPrice() * quantity;

        double fee = calculateSellFee(user, asset, quantity);
        double finalValue = totalValue - fee;

        if (fee > 0) {
            System.out.printf("Taxa aplicata: %.2f RON (5%%)\n", fee);
        }

        user.setBalance(user.getBalance() + finalValue);

        user.removeFromPortfolio(asset.getSymbol(), quantity);

        asset.setQuantity(asset.getQuantity() + quantity);

        Transaction transaction = new Transaction(
                asset.getSymbol(), asset.getName(), quantity, asset.getPrice(), "SELL");
        user.addTransaction(transaction);

        System.out.printf("Vanzare reusita: %.2f x %s la %.2f RON = %.2f RON\n",
                quantity, asset.getSymbol(), asset.getPrice(), finalValue);

        return true;
    }

    protected abstract boolean canBuy(User user, Asset asset, double quantity);
    protected abstract boolean canSell(User user, Asset asset, double quantity);
    protected abstract double calculateSellFee(User user, Asset asset, double quantity);
}