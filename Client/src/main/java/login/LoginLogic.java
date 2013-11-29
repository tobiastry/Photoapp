package login;

/**
 *
 * @author Stian
 */
public class LoginLogic {

    private static final int PASSWORD = 1234;

    /**
     * Checks the given string against the hard coded password.
     *
     * @param password
     * @return true if password is correct
     */
    public static boolean checkLogin(String password) {
        int pw;
        try {
            pw = Integer.parseInt(password);
        } catch (NumberFormatException e) {
            return false;
        }

        if (pw == PASSWORD) {
            return true;
        }
        return false;
    }
}
