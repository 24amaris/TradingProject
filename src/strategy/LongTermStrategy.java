package strategy;

import model.Asset;
import model.User;

/**
 * PATTERN: STRATEGY - Implementare concreta
 *
 * LongTermStrategy: Investitie pe termen lung.
 * - Doar pentru actiuni (nu crypto)
 * - Fara taxe suplimentare
 * - Se respecta orele de piata
 */
public class LongTermStrategy extends BaseTradingStrategy {

    @Override
    public String getStrategyName() {
        return "Long Term Investment";
    }

    @Override
    protected boolean canBuy(User user, Asset asset, double quantity) {
        if (asset.isCrypto()) {
            System.out.println("Long Term Investment este disponibil doar pentru actiuni!");
            return false;
        }

        return isStockTradingAllowed(asset);
    }

    @Override
    protected boolean canSell(User user, Asset asset, double quantity) {
        if (asset.isCrypto()) {
            System.out.println("Long Term Investment este disponibil doar pentru actiuni!");
            return false;
        }
        return isStockTradingAllowed(asset);
    }

    @Override
    protected double calculateSellFee(User user, Asset asset, double quantity) {
        return 0;
    }
}