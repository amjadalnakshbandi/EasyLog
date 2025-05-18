package constants;



public class constants {

    public static final String NAME_RULE = "^[A-Za-z'-]{1,50}$";
    public static final String EMAIL_RULE = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";  // john.doe@example.com
    public static final String PASSWORD_RULE = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#^()_+=-])[A-Za-z\\d@$!%*?&#^()_+=-]{8,}$";
    /*
    * At least 8 characters
    * At least one uppercase, one lowercase, one digit, and one special character
    * */
    public static final String CSV_USER_PATH = "Data/users.csv";
    public static final String CSV_Login_PATH = "Data/logins.csv";
    public static final String CSV_Orders_PATH = "Data/orders.csv";
    public static final String SUPER_ADMIN_TOKEN = "ASE";

}
