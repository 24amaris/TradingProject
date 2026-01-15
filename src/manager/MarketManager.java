package manager;
import model.Asset;
import java.util.ArrayList;
import java.util.List;
/**
 * PATTERN: SINGLETON
 *
 * MarketManager este un Singleton deoarece:
 * - Trebuie sa existe o singura instanta care gestioneaza piata
 * - Toate componentele aplicatiei trebuie sa acceseze aceeasi lista de active
 * - Previne duplicarea datelor si inconsistentele
 *
 * Singleton = o clasa care are o singura instanta in toata aplicatia
 */

public class MarketManager {

    private static MarketManager instance;

    private List<Asset> assets;
    private MarketManager() {
        assets = new ArrayList<>();
        initializeMarket();
    }

    public static MarketManager getInstance() {
        if (instance == null) {
            instance = new MarketManager();
        }
        return instance;
    }

    private void initializeMarket() {
        assets.add(new Asset("Apple Inc", "AAPL", 150.0, 1000, false));
        assets.add(new Asset("Microsoft", "MSFT", 300.0, 1000, false));
        assets.add(new Asset("Tesla", "TSLA", 200.0, 1000, false));

        assets.add(new Asset("Bitcoin", "BTC", 45000.0, 100, true));
        assets.add(new Asset("Ethereum", "ETH", 3000.0, 500, true));
        assets.add(new Asset("Dogecoin", "DOGE", 0.15, 100000, true));
    }

    public List<Asset> getAllAssets() {
        return assets;
    }

    public Asset findBySymbol(String symbol) {
        for (Asset asset : assets) {
            if (asset.getSymbol().equalsIgnoreCase(symbol)) {
                return asset;
            }
        }
        return null;
    }

    public List<Asset> findByName(String name) {
        List<Asset> results = new ArrayList<>();
        for (Asset asset : assets) {
            if (asset.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(asset);
            }
        }
        return results;
    }

    public void updatePrice(String symbol, double newPrice) {
        Asset asset = findBySymbol(symbol);
        if (asset != null) {
            double oldPrice = asset.getPrice();
            asset.setPrice(newPrice);

            NotificationManager.getInstance().notifyPriceChange(asset, oldPrice, newPrice);
        }
    }
}