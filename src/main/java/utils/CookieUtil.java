package utils;

import driver.DriverManager;
import io.restassured.http.Cookies;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;

public class CookieUtil {
    public static List<Cookie> convertRestAssuredCookiesToSeleniumCookies(Cookies cookies) {
        List<io.restassured.http.Cookie> restAssuredCookies = cookies.asList();
        List<Cookie> seleniumCookies = new ArrayList<>();
        for (io.restassured.http.Cookie cookie : restAssuredCookies) {

            seleniumCookies.add(new Cookie(cookie.getName(), cookie.getValue(), cookie.getDomain()
                    , cookie.getPath(), cookie.getExpiryDate(), cookie.isSecured(), cookie.isHttpOnly(), cookie.getSameSite()));
        }
        return seleniumCookies;
    }

    public static void injectCookiesToBrowser(Cookies cookies) {
        List<Cookie> seleniumCookies = convertRestAssuredCookiesToSeleniumCookies(cookies);
        for (Cookie cookie : seleniumCookies) {
            DriverManager.getDriver().manage().addCookie(cookie);
        }
    }
}