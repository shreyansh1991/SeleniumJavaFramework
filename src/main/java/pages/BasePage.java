package pages;

import driver.DriverManager;
import enums.WaitStrategy;
import factories.ExplicitWaitFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DropdownUtil;
import utils.ElementUtil;
import utils.JavaScriptUtil;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavaScriptUtil jsUtil;
    protected DropdownUtil dropdownUtil;
    protected ElementUtil elementUtil;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        jsUtil = new JavaScriptUtil(driver);
        dropdownUtil = new DropdownUtil(driver);
        elementUtil = new ElementUtil(driver);
    }

    public void load(String endPoint) {
        driver.get("https://www.askomdch.com" + endPoint);
    }

    public void waitForOverlaysToDisappear(By overlays) {
        List<WebElement> overLays = driver.findElements(overlays);
        if (overLays.size() > 0) {
            System.out.println("Overlays size :: " + overLays.size());
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlays));
        }
        System.out.println("Overlays Disappeared");
    }

    protected void click(By by, WaitStrategy waitstrategy, String elementname) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitstrategy, by);
        element.click();
        //  log(PASS,elementname+" is clicked");

    }

    protected void sendKeys(By by, String value, WaitStrategy waitstrategy, String elementname) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitstrategy, by);
        element.sendKeys(value);
        //log(PASS,value +" is entered successfully in "+elementname);
    }

    protected String getPageTitle() {
        return DriverManager.getDriver().getTitle();
    }

    protected abstract BasePage isLoaded();


}
