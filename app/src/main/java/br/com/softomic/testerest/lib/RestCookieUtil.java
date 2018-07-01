package br.com.softomic.testerest.lib;

public class RestCookieUtil {
    public static String[] getCookies(String strCookies) {
        String[] cookies = strCookies.split(";");

        return cookies;
    }
}
