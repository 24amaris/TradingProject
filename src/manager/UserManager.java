package manager;
import model.User;
import java.util.HashMap;
import java.util.Map;
/**
 * PATTERN: SINGLETON
 *
 * UserManager este Singleton pentru aceleasi motive ca MarketManager:
 * - O singura instanta gestioneaza toti utilizatorii
 * - Asigura consistenta datelor
 */
public class UserManager {

    private static UserManager instance;
    private Map<String, User> users;

    private User currentUser;

    private UserManager() {
        users = new HashMap<>();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        User newUser = new User(username, password);
        users.put(username, newUser);
        return true;
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}