package org.selenium.base;

import driver.Driver;
import driver.DriverManager;
import enums.WebBrowserType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class BaseTest {

    @Parameters("browser")
    @BeforeMethod
    public void beforeMethod(String browser) {
        Driver.initDriver(browser);
        Driver.launchURL();

    }

    @AfterMethod
    public void tearDown() {
       // Driver.quitDriver();
    }
}
