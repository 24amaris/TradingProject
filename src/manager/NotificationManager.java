package manager;
import model.Asset;
import model.User;
import observer.PriceObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * PATTERN: OBSERVER (Subject) + SINGLETON
 *
 * NotificationManager este "Subject"-ul din pattern-ul Observer.
 * El mentine o lista de observatori si ii notifica cand pretul se schimba.
 *
 * De ce Observer?
 * - Utilizatorii pot urmari active (watch list)
 * - Cand pretul se schimba, ei sunt notificati automat
 * - Nu trebuie sa verifice manual pretul mereu
 */
public class NotificationManager {

    private static NotificationManager instance;
    private List<PriceObserver> observers;
    private NotificationManager() {
        observers = new ArrayList<>();
    }

    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }

    public void addObserver(PriceObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(PriceObserver observer) {
        observers.remove(observer);
    }

    public void notifyPriceChange(Asset asset, double oldPrice, double newPrice) {
        for (PriceObserver observer : observers) {
            observer.onPriceChange(asset, oldPrice, newPrice);
        }
    }
}