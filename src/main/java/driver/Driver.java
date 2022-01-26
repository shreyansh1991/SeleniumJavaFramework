package driver;

import com.fasterxml.jackson.core.type.TypeReference;
import enums.EnvType;
import enums.WebBrowserType;
import factories.DriverFactory;
import factories.EnvFactory;
import utils.JacksonUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;


public final class Driver {

    /**
     * Private constructor to avoid external instantiation
     */
    private Driver() {
    }


    public static void initDriver(String browser) {
        browser = System.getProperty("browser", browser);
        WebBrowserType browserType = getBrowserType(browser);

        if (Objects.isNull(DriverManager.getDriver())) {
            DriverManager.setDriver(DriverFactory.getDriver(browserType));
        }
    }

    public static void launchURL() {
        String envName = System.getProperty("env", String.valueOf(EnvType.STAGE));
        DriverManager.getDriver().get(EnvFactory.getURL(EnvType.valueOf(envName)));
        DriverManager.getDriver().manage().window().maximize();
    }


    public static void quitDriver() {
        if (Objects.nonNull(DriverManager.getDriver())) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
    }

    private static WebBrowserType getBrowserType(String browser) {
        WebBrowserType browserType = null;
        if (browser != null) {
            try {
                browserType = WebBrowserType.valueOf(browser.toUpperCase());
            } catch (IllegalArgumentException exception) {
                System.out.println("Please pass the correct Browser name " + browser + " is not supported");
                throw new IllegalArgumentException("Please pass the correct Browser name");
            }
        }
        return browserType;


    }
}