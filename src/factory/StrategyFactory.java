package factory;
import strategy.*;

/**
 * PATTERN: SIMPLE FACTORY
 *
 * Simple Factory e un pattern care creeaza obiecte fara a expune
 * logica de creare catre client. Clientul cere un obiect si factory-ul
 * il returneaza.
 *
 * De ce Simple Factory?
 * - Centralizam crearea strategiilor intr-un singur loc
 * - Clientul nu trebuie sa stie despre toate clasele de strategii
 * - Usor de adaugat noi strategii in viitor
 */
public class StrategyFactory {

    public static TradingStrategy createStrategy(String type) {
        switch (type.toLowerCase()) {
            case "day":
                return new DayTradingStrategy();
            case "long":
                return new LongTermStrategy();
            case "crypto":
                return new CryptoTradingStrategy();
            case "auto":
                return new AutoTradeStrategy();
            default:
                System.out.println("Tip de strategie necunoscut: " + type);
                return null;
        }
    }

    public static void showAvailableStrategies() {
        System.out.println("\nStrategii disponibile:");
        System.out.println("1. day   - Day Trading (actiuni, vanzare in aceeasi zi)");
        System.out.println("2. long  - Long Term Investment (actiuni, fara taxa)");
        System.out.println("3. crypto - Crypto Trading (crypto, 24/7)");
        System.out.println("4. auto  - Auto Trade (cu praguri de pret)");
    }
}