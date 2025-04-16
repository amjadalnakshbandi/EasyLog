package constants;



public class constants {

    public static final String NAME_RULE = "^[A-Za-z'-]{1,50}$";
    public static final String EMAIL_RULE = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";  // john.doe@example.com
    public static final String PASSWORD_RULE = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#^()_+=-])[A-Za-z\\d@$!%*?&#^()_+=-]{8,}$";
    /*
    * At least 8 characters
    * At least one uppercase, one lowercase, one digit, and one special character
    * */
}
