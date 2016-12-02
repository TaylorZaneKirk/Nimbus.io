package couldmsu.project.nimbusio;

/**
 * Created by VishnuChaitanya on 11/25/2016.
 */

public class ConfigPayment {

    public static final String DATA_URL = "http://104.236.47.53/paymentfetch.php?uid=";
    public static final String KEY_CCN = "cardnumber";     // credit card number
    public static final String KEY_CCUN = "nameoncard";  // cc user name
    public static final String KEY_CCED = "expdate";   // cc expiration date
    public static final String KEY_CCI = "ccissuer";       // cc issuer
    public static final String KEY_CCCVV = "cvv";     // Security code
    public static final String KEY_CCAddress1 = "addressl1";  // cc address line 1
    public static final String KEY_CCAddress2 = "addressl2";  // cc address line 2
    public static final String KEY_CCZIP = "zipcode";       // cc user zipcode
    public static final String JSON_ARRAY = "result";

}
