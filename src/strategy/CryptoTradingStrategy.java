package strategy;

import model.Asset;
import model.User;

/**
 * PATTERN: STRATEGY - Implementare concreta
 *
 * CryptoTradingStrategy: Tranzactionare criptomonede.
 * - Doar pentru crypto
 * - Disponibil 24/7 (fara restrictii de timp)
 * - Fara taxe
 */
public class CryptoTradingStrategy extends BaseTradingStrategy {

    @Override
    public String getStrategyName() {
        return "Crypto Trading";
    }

    @Override
    protected boolean canBuy(User user, Asset asset, double quantity) {
        if (!asset.isCrypto()) {
            System.out.println("Crypto Trading este disponibil doar pentru criptomonede!");
            return false;
        }

        return true;
    }

    @Override
    protected boolean canSell(User user, Asset asset, double quantity) {
        if (!asset.isCrypto()) {
            System.out.println("Crypto Trading este disponibil doar pentru criptomonede!");
            return false;
        }
        return true;
    }

    @Override
    protected double calculateSellFee(User user, Asset asset, double quantity) {
        return 0;
    }
}