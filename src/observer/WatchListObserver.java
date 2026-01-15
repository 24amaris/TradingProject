package observer;

import model.Asset;
import model.User;

/**
 * PATTERN: OBSERVER - Implementare concreta
 *
 * WatchListObserver verifica daca activul al carui pret s-a schimbat
 * este in lista de urmarire a utilizatorului si afiseaza notificarea.
 */
public class WatchListObserver implements PriceObserver {

    private User user;
    public WatchListObserver(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void onPriceChange(Asset asset, double oldPrice, double newPrice) {

        if (user.getWatchList().contains(asset.getSymbol())) {
            String direction = newPrice > oldPrice ? "CRESCUT" : "SCAZUT";
            double percent = ((newPrice - oldPrice) / oldPrice) * 100;

            System.out.println("\n*** NOTIFICARE pentru " + user.getUsername() + " ***");
            System.out.printf("Pretul pentru %s (%s) a %s: %.2f -> %.2f (%.2f%%)\n",
                    asset.getName(), asset.getSymbol(), direction, oldPrice, newPrice, percent);
            System.out.println("*".repeat(45));
        }
    }
}