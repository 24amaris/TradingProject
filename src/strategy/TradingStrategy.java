package strategy;

import model.Asset;
import model.User;

/**
 * PATTERN: STRATEGY - Interfata Strategy
 *
 * Strategy este un pattern care permite schimbarea algoritmului/comportamentului
 * la runtime. In cazul nostru, avem diferite strategii de tranzactionare.
 *
 * De ce Strategy?
 * - Avem mai multe moduri de a tranzactiona (day trading, long term, auto)
 * - Fiecare are reguli diferite
 * - Utilizatorul poate schimba strategia oricand
 * - Evitam if-uri multiple in cod
 */
public interface TradingStrategy {

    boolean buy(User user, Asset asset, double quantity);

    boolean sell(User user, Asset asset, double quantity);

    String getStrategyName();
}