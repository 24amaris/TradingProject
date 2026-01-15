package strategy;

import model.Asset;
import model.PortfolioItem;
import model.User;
import java.time.LocalDate;

/**
 * PATTERN: STRATEGY - Implementare concreta
 *
 * DayTradingStrategy: Cumparare si vanzare in aceeasi zi.
 * - Doar pentru actiuni (nu crypto)
 * - Daca nu vinzi in aceeasi zi, se aplica taxa de 5%
 */
public class DayTradingStrategy extends BaseTradingStrategy {

    @Override
    public String getStrategyName() {
        return "Day Trading";
    }

    @Override
    protected boolean canBuy(User user, Asset asset, double quantity) {
        if (asset.isCrypto()) {
            System.out.println("Day Trading este disponibil doar pentru actiuni, nu crypto!");
            return false;
        }

        return isStockTradingAllowed(asset);
    }

    @Override
    protected boolean canSell(User user, Asset asset, double quantity) {
        if (asset.isCrypto()) {
            System.out.println("Day Trading este disponibil doar pentru actiuni!");
            return false;
        }
        return isStockTradingAllowed(asset);
    }

    @Override
    protected double calculateSellFee(User user, Asset asset, double quantity) {
        PortfolioItem item = user.getPortfolio().get(asset.getSymbol());

        if (item != null) {
            LocalDate purchaseDate = item.getPurchaseDate();
            LocalDate today = LocalDate.now();

            if (!purchaseDate.equals(today)) {
                double totalValue = asset.getPrice() * quantity;
                System.out.println("ATENTIE: Vanzare in alta zi decat cumpararea - se aplica taxa 5%!");
                return totalValue * 0.05;
            }
        }

        return 0;
    }
}