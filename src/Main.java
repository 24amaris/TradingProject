import facade.TradingFacade;
import factory.StrategyFactory;

import java.util.Scanner;
public class Main {

    private static TradingFacade facade = new TradingFacade();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   PLATFORMA DE TRADING - BINE AI VENIT ║");
        System.out.println("╚════════════════════════════════════════╝");

        boolean running = true;

        while (running) {
            showMenu();
            System.out.print("\nAlege optiunea: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleRegister();
                    break;
                case "2":
                    handleLogin();
                    break;
                case "3":
                    facade.logout();
                    break;
                case "4":
                    facade.showMarket();
                    break;
                case "5":
                    handleSearch();
                    break;
                case "6":
                    handleBuy();
                    break;
                case "7":
                    handleSell();
                    break;
                case "8":
                    facade.showPortfolio();
                    break;
                case "9":
                    facade.showHistory();
                    break;
                case "10":
                    handleStrategy();
                    break;
                case "11":
                    handleWatchList();
                    break;
                case "12":
                    handleAutoTrade();
                    break;
                case "13":
                    handlePriceSimulation();
                    break;
                case "0":
                    running = false;
                    System.out.println("La revedere!");
                    break;
                default:
                    System.out.println("Optiune invalida!");
            }
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n╔═══════════════ MENIU ═══════════════╗");

        if (facade.isLoggedIn()) {
            System.out.println("║ Status: LOGAT                       ║");
            System.out.println("║ Strategie: " + padRight(facade.getCurrentStrategyName(), 24) + "║");
        } else {
            System.out.println("║ Status: NELOGAT                     ║");
        }
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║ AUTENTIFICARE                       ║");
        System.out.println("║  1. Inregistrare                    ║");
        System.out.println("║  2. Logare                          ║");
        System.out.println("║  3. Delogare                        ║");
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║ PIATA                               ║");
        System.out.println("║  4. Vezi piata                      ║");
        System.out.println("║  5. Cauta activ                     ║");
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║ TRANZACTIONARE                      ║");
        System.out.println("║  6. Cumpara                         ║");
        System.out.println("║  7. Vinde                           ║");
        System.out.println("║  8. Vezi portofoliu                 ║");
        System.out.println("║  9. Vezi istoric                    ║");
        System.out.println("║ 10. Schimba strategie               ║");
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║ ALTELE                              ║");
        System.out.println("║ 11. Watch List                      ║");
        System.out.println("║ 12. Auto-Trade (praguri)            ║");
        System.out.println("║ 13. Simuleaza schimbare pret        ║");
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║  0. Iesire                          ║");
        System.out.println("╚═════════════════════════════════════╝");
    }

    private static void handleRegister() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Parola: ");
        String password = scanner.nextLine();
        facade.register(username, password);
    }

    private static void handleLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Parola: ");
        String password = scanner.nextLine();
        facade.login(username, password);
    }

    /**
     * cautarea
     */
    private static void handleSearch() {
        System.out.print("Introdu simbolul sau numele: ");
        String query = scanner.nextLine();
        facade.searchAsset(query);
    }

    /**
     * cumpararea
     */
    private static void handleBuy() {
        System.out.print("Simbol activ (ex: BTC, AAPL): ");
        String symbol = scanner.nextLine().toUpperCase();
        System.out.print("Cantitate: ");
        try {
            double quantity = Double.parseDouble(scanner.nextLine());
            facade.buy(symbol, quantity);
        } catch (NumberFormatException e) {
            System.out.println("Cantitate invalida!");
        }
    }

    /**
     *  vanzarea
     */
    private static void handleSell() {
        System.out.print("Simbol activ (ex: BTC, AAPL): ");
        String symbol = scanner.nextLine().toUpperCase();
        System.out.print("Cantitate: ");
        try {
            double quantity = Double.parseDouble(scanner.nextLine());
            facade.sell(symbol, quantity);
        } catch (NumberFormatException e) {
            System.out.println("Cantitate invalida!");
        }
    }

    /**
     *  schimbarea strategiei
     */
    private static void handleStrategy() {
        StrategyFactory.showAvailableStrategies();
        System.out.print("Alege strategia (day/long/crypto/auto): ");
        String type = scanner.nextLine();
        facade.setStrategy(type);
    }

    /**
     * Gestioneaza watch list-ul
     */
    private static void handleWatchList() {
        System.out.println("\n1. Vezi watch list");
        System.out.println("2. Adauga in watch list");
        System.out.println("3. Sterge din watch list");
        System.out.print("Alege: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                facade.showWatchList();
                break;
            case "2":
                System.out.print("Simbol de adaugat: ");
                facade.addToWatchList(scanner.nextLine().toUpperCase());
                break;
            case "3":
                System.out.print("Simbol de sters: ");
                facade.removeFromWatchList(scanner.nextLine().toUpperCase());
                break;
            default:
                System.out.println("Optiune invalida!");
        }
    }
    /**
     *  setarile auto-trade
     */
    private static void handleAutoTrade() {
        System.out.println("\n1. Vezi praguri setate");
        System.out.println("2. Seteaza auto-buy (cumpara cand pretul scade)");
        System.out.println("3. Seteaza auto-sell (vinde cand pretul creste)");
        System.out.print("Alege: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                facade.showAutoThresholds();
                break;
            case "2":
                handleSetAutoBuy();
                break;
            case "3":
                handleSetAutoSell();
                break;
            default:
                System.out.println("Optiune invalida!");
        }
    }

    private static void handleSetAutoBuy() {
        try {
            System.out.print("Simbol: ");
            String symbol = scanner.nextLine().toUpperCase();
            System.out.print("Pret sub care sa cumpere: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Cantitate de cumparat: ");
            double quantity = Double.parseDouble(scanner.nextLine());
            facade.setAutoBuy(symbol, price, quantity);
        } catch (NumberFormatException e) {
            System.out.println("Valoare invalida!");
        }
    }

    private static void handleSetAutoSell() {
        try {
            System.out.print("Simbol: ");
            String symbol = scanner.nextLine().toUpperCase();
            System.out.print("Pret peste care sa vanda: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Cantitate de vandut: ");
            double quantity = Double.parseDouble(scanner.nextLine());
            facade.setAutoSell(symbol, price, quantity);
        } catch (NumberFormatException e) {
            System.out.println("Valoare invalida!");
        }
    }

    /**
     *  simularea schimbarii de pret
     */
    private static void handlePriceSimulation() {
        try {
            System.out.print("Simbol: ");
            String symbol = scanner.nextLine().toUpperCase();
            System.out.print("Noul pret: ");
            double price = Double.parseDouble(scanner.nextLine());
            facade.simulatePriceChange(symbol, price);
        } catch (NumberFormatException e) {
            System.out.println("Pret invalid!");
        }
    }

    /**
     * Fct pt formatare
     */
    private static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}