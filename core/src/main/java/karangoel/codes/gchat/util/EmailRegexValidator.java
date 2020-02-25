package karangoel.codes.gchat.util;

import java.util.regex.Pattern;

final public class EmailRegexValidator {
    public static boolean check(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."  +
                "[a-zA-Z0-9_+&*-]+)*@"     +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z"  +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
