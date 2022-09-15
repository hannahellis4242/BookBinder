package utils;

import java.util.regex.Pattern;

public class StringUtils {
    private final static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public static String plural(String str, int number) {
        return number != 1 ? str + "s" : str;
    }
}
