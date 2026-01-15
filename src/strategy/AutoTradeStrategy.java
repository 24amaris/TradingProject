package strategy;

import model.Asset;
import model.User;
import observer.PriceObserver;
import manager.NotificationManager;
import java.util.HashMap;
import java.util.Map;

/**
 * PATTERN: STRATEGY + OBSERVER
 *
 * AutoTradeStrategy: Cumparare/vanzare automata bazata pe praguri de pret.
 * Foloseste si Observer pattern pentru a reactiona la schimbarile de pret.
 */
public class AutoTradeStrategy extends BaseTradingStrategy implements PriceObserver {

    private Map<String, Double> autoBuyThresholds;

    private Map<String, Double> autoSellThresholds;

    private Map<String, Double> autoQuantities;

    private User user;

    public AutoTradeStrategy() {
        autoBuyThresholds = new HashMap<>();
        autoSellThresholds = new HashMap<>();
        autoQuantities = new HashMap<>();
    }

    public void setUser(User user) {
        this.user = user;
        NotificationManager.getInstance().addObserver(this);
    }

    public void setAutoBuyThreshold(String symbol, double price, double quantity) {
        autoBuyThresholds.put(symbol, price);
        autoQuantities.put(symbol + "_buy", quantity);
        System.out.printf("Auto-buy setat: Cumpara %.2f %s cand pretul scade sub %.2f\n",
                quantity, symbol, price);
    }

    public void setAutoSellThreshold(String symbol, double price, double quantity) {
        autoSellThresholds.put(symbol, price);
        autoQuantities.put(symbol + "_sell", quantity);
        System.out.printf("Auto-sell setat: Vinde %.2f %s cand pretul creste peste %.2f\n",
                quantity, symbol, price);
    }

    @Override
    public void onPriceChange(Asset asset, double oldPrice, double newPrice) {
        if (user == null) return;

        String symbol = asset.getSymbol();

        if (autoBuyThresholds.containsKey(symbol)) {
            double threshold = autoBuyThresholds.get(symbol);
            if (newPrice <= threshold && oldPrice > threshold) {
                Double qty = autoQuantities.get(symbol + "_buy");
                if (qty != null) {
                    System.out.println("\n*** AUTO-BUY DECLANSAT ***");
                    buy(user, asset, qty);
                    autoBuyThresholds.remove(symbol);
                }
            }
        }

        if (autoSellThresholds.containsKey(symbol)) {
            double threshold = autoSellThresholds.get(symbol);
            if (newPrice >= threshold && oldPrice < threshold) {
                Double qty = autoQuantities.get(symbol + "_sell");
                if (qty != null) {
                    System.out.println("\n*** AUTO-SELL DECLANSAT ***");
                    sell(user, asset, qty);
                    autoSellThresholds.remove(symbol);
                }
            }
        }
    }

    @Override
    public String getStrategyName() {
        return "Auto Trade";
    }

    @Override
    protected boolean canBuy(User user, Asset asset, double quantity) {
        return isStockTradingAllowed(asset);
    }

    @Override
    protected boolean canSell(User user, Asset asset, double quantity) {
        return isStockTradingAllowed(asset);
    }

    @Override
    protected double calculateSellFee(User user, Asset asset, double quantity) {
        return 0;
    }

    public void showThresholds() {
        System.out.println("\n=== Praguri Auto-Trade ===");
        if (autoBuyThresholds.isEmpty() && autoSellThresholds.isEmpty()) {
            System.out.println("Niciun prag setat.");
            return;
        }

        for (Map.Entry<String, Double> entry : autoBuyThresholds.entrySet()) {
            Double qty = autoQuantities.get(entry.getKey() + "_buy");
            System.out.printf("AUTO-BUY: %s - cand pret < %.2f, cantitate: %.2f\n",
                    entry.getKey(), entry.getValue(), qty);
        }

        for (Map.Entry<String, Double> entry : autoSellThresholds.entrySet()) {
            Double qty = autoQuantities.get(entry.getKey() + "_sell");
            System.out.printf("AUTO-SELL: %s - cand pret > %.2f, cantitate: %.2f\n",
                    entry.getKey(), entry.getValue(), qty);
        }
    }
}