package org.selenium.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class HandleStale {
    @Test
    public void test21()
    {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        driver.manage().window().maximize();
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement element = driver.findElement(By.id("txtUsername"));
        driver.navigate().refresh();
        waitForElementToBeDisplayed(wait,driver,By.id("txtUsername")).sendKeys("Testing");
//        driver.findElement(By.name("q")).sendKeys("Testing");
        driver.close();

    }
    public WebElement waitForElementToBeDisplayed(WebDriverWait wait, WebDriver driver,By by) {
        wait.until(d -> driver.findElement(by).isDisplayed() && driver.findElement(by).isEnabled());
        return wait.until(d -> driver.findElement(by));
    }

}
