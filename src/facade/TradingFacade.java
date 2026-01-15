package facade;
import factory.StrategyFactory;
import manager.MarketManager;
import manager.NotificationManager;
import manager.UserManager;
import model.Asset;
import model.PortfolioItem;
import model.Transaction;
import model.User;
import observer.WatchListObserver;
import strategy.AutoTradeStrategy;
import strategy.TradingStrategy;
import java.util.List;
/**
 * PATTERN: FACADE
 * Facade = pattern care ofera o interfata simplificata catre
 * un subsistem complex. In loc sa interactionam cu multe clase,
 * interactionam doar cu Facade-ul.
 *
 * De ce Facade?
 * - Sistemul nostru are multe componente: UserManager, MarketManager,
 *   NotificationManager, Strategii, etc.
 * - Facade-ul ofera metode simple pentru actiunile comune
 * - Clientul (Main) nu trebuie sa stie detaliile interne
 */

public class TradingFacade {

    private UserManager userManager;
    private MarketManager marketManager;
    private NotificationManager notificationManager;
    private TradingStrategy currentStrategy;
    private WatchListObserver currentObserver;

    public TradingFacade() {
        userManager = UserManager.getInstance();
        marketManager = MarketManager.getInstance();
        notificationManager = NotificationManager.getInstance();

        currentStrategy = StrategyFactory.createStrategy("long");
    }

    // ============ AUTENTIFICARE ============

    public boolean register(String username, String password) {
        boolean success = userManager.register(username, password);
        if (success) {
            System.out.println("Inregistrare reusita! Te poti loga acum.");
        } else {
            System.out.println("Username-ul exista deja!");
        }
        return success;
    }

    public boolean login(String username, String password) {
        boolean success = userManager.login(username, password);
        if (success) {
            System.out.println("Bine ai venit, " + username + "!");

            // observer pt notificari
            currentObserver = new WatchListObserver(userManager.getCurrentUser());
            notificationManager.addObserver(currentObserver);

            if (currentStrategy instanceof AutoTradeStrategy) {
                ((AutoTradeStrategy) currentStrategy).setUser(userManager.getCurrentUser());
            }
        } else {
            System.out.println("Username sau parola gresita!");
        }
        return success;
    }


    public void logout() {
        if (currentObserver != null) {
            notificationManager.removeObserver(currentObserver);
            currentObserver = null;
        }
        userManager.logout();
        System.out.println("Te-ai delogat cu succes!");
    }


    public boolean isLoggedIn() {
        return userManager.isLoggedIn();
    }

    // ============ VIZUALIZARE PIATA ============

    public void showMarket() {
        System.out.println("\n========== PIATA ==========");
        List<Asset> assets = marketManager.getAllAssets();

        System.out.println("\n--- ACTIUNI ---");
        for (Asset asset : assets) {
            if (!asset.isCrypto()) {
                System.out.println(asset);
            }
        }

        System.out.println("\n--- CRIPTOMONEDE ---");
        for (Asset asset : assets) {
            if (asset.isCrypto()) {
                System.out.println(asset);
            }
        }
        System.out.println("==============================");
    }


    public void searchAsset(String query) {
        System.out.println("\nRezultate cautare pentru: " + query);

        Asset bySymbol = marketManager.findBySymbol(query);
        if (bySymbol != null) {
            System.out.println("Gasit dupa simbol: " + bySymbol);
            return;
        }

        List<Asset> byName = marketManager.findByName(query);
        if (!byName.isEmpty()) {
            System.out.println("Gasite dupa nume:");
            for (Asset asset : byName) {
                System.out.println("  " + asset);
            }
        } else {
            System.out.println("Niciun rezultat gasit.");
        }
    }

    // ============ TRANZACTIONARE ============


    public void setStrategy(String type) {
        TradingStrategy newStrategy = StrategyFactory.createStrategy(type);
        if (newStrategy != null) {
            currentStrategy = newStrategy;
            System.out.println("Strategie schimbata la: " + currentStrategy.getStrategyName());


            if (currentStrategy instanceof AutoTradeStrategy && userManager.isLoggedIn()) {
                ((AutoTradeStrategy) currentStrategy).setUser(userManager.getCurrentUser());
            }
        }
    }

    public String getCurrentStrategyName() {
        return currentStrategy.getStrategyName();
    }

    public boolean buy(String symbol, double quantity) {
        if (!userManager.isLoggedIn()) {
            System.out.println("Trebuie sa fii logat pentru a tranzactiona!");
            return false;
        }

        Asset asset = marketManager.findBySymbol(symbol);
        if (asset == null) {
            System.out.println("Activul " + symbol + " nu a fost gasit!");
            return false;
        }

        return currentStrategy.buy(userManager.getCurrentUser(), asset, quantity);
    }


    public boolean sell(String symbol, double quantity) {
        if (!userManager.isLoggedIn()) {
            System.out.println("Trebuie sa fii logat pentru a tranzactiona!");
            return false;
        }

        Asset asset = marketManager.findBySymbol(symbol);
        if (asset == null) {
            System.out.println("Activul " + symbol + " nu a fost gasit!");
            return false;
        }

        return currentStrategy.sell(userManager.getCurrentUser(), asset, quantity);
    }

    // ============ AUTO-TRADE ============


    public void setAutoBuy(String symbol, double price, double quantity) {
        if (!(currentStrategy instanceof AutoTradeStrategy)) {
            System.out.println("Schimba strategia la 'auto' pentru a folosi auto-trade!");
            return;
        }
        ((AutoTradeStrategy) currentStrategy).setAutoBuyThreshold(symbol, price, quantity);
    }

    public void setAutoSell(String symbol, double price, double quantity) {
        if (!(currentStrategy instanceof AutoTradeStrategy)) {
            System.out.println("Schimba strategia la 'auto' pentru a folosi auto-trade!");
            return;
        }
        ((AutoTradeStrategy) currentStrategy).setAutoSellThreshold(symbol, price, quantity);
    }


    public void showAutoThresholds() {
        if (currentStrategy instanceof AutoTradeStrategy) {
            ((AutoTradeStrategy) currentStrategy).showThresholds();
        } else {
            System.out.println("Auto-trade nu este activ.");
        }
    }

    // ============ PORTOFOLIU ============


    public void showPortfolio() {
        if (!userManager.isLoggedIn()) {
            System.out.println("Trebuie sa fii logat!");
            return;
        }

        User user = userManager.getCurrentUser();
        System.out.println("\n========== PORTOFOLIU ==========");
        System.out.printf("Sold disponibil: %.2f RON\n\n", user.getBalance());

        if (user.getPortfolio().isEmpty()) {
            System.out.println("Portofoliul este gol.");
        } else {
            double totalValue = 0;
            for (PortfolioItem item : user.getPortfolio().values()) {
                System.out.println(item);
                totalValue += item.getQuantity() * item.getAsset().getPrice();
            }
            System.out.printf("\nValoare totala portofoliu: %.2f RON\n", totalValue);
            System.out.printf("Valoare totala (sold + portofoliu): %.2f RON\n",
                    user.getBalance() + totalValue);
        }
        System.out.println("==================================");
    }

    // ============ ISTORIC ============


    public void showHistory() {
        if (!userManager.isLoggedIn()) {
            System.out.println("Trebuie sa fii logat!");
            return;
        }

        User user = userManager.getCurrentUser();
        System.out.println("\n========== ISTORIC TRANZACTII ==========");

        List<Transaction> history = user.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("Nicio tranzactie efectuata.");
        } else {
            for (Transaction t : history) {
                System.out.println(t);
            }
        }
        System.out.println("==========================================");
    }

    // ============ WATCH LIST ============

    public void addToWatchList(String symbol) {
        if (!userManager.isLoggedIn()) {
            System.out.println("Trebuie sa fii logat!");
            return;
        }

        Asset asset = marketManager.findBySymbol(symbol);
        if (asset == null) {
            System.out.println("Activul " + symbol + " nu exista!");
            return;
        }

        userManager.getCurrentUser().addToWatchList(symbol);
        System.out.println(symbol + " adaugat in lista de urmarire.");
    }

    public void removeFromWatchList(String symbol) {
        if (!userManager.isLoggedIn()) {
            System.out.println("Trebuie sa fii logat!");
            return;
        }

        userManager.getCurrentUser().removeFromWatchList(symbol);
        System.out.println(symbol + " sters din lista de urmarire.");
    }

    public void showWatchList() {
        if (!userManager.isLoggedIn()) {
            System.out.println("Trebuie sa fii logat!");
            return;
        }

        List<String> watchList = userManager.getCurrentUser().getWatchList();
        System.out.println("\n========== WATCH LIST ==========");

        if (watchList.isEmpty()) {
            System.out.println("Lista de urmarire este goala.");
        } else {
            for (String symbol : watchList) {
                Asset asset = marketManager.findBySymbol(symbol);
                if (asset != null) {
                    System.out.println(asset);
                }
            }
        }
        System.out.println("==================================");
    }

    // ============ SIMULARE PRET ============

    public void simulatePriceChange(String symbol, double newPrice) {
        marketManager.updatePrice(symbol, newPrice);
        System.out.println("Pretul pentru " + symbol + " actualizat la " + newPrice);
    }
}