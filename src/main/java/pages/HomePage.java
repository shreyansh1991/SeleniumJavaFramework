package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By storeMenuLink = By.cssSelector("li[id='menu-item-1227'] a[class='menu-link']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public HomePage isLoaded() {
        System.out.println(driver.getTitle());
        wait.until(d->driver.getTitle().contains("AskOmDch – Become a Selenium automation expert!"));
        return this;
    }

    public StorePage navigateToStoreUsingMenu() {
        elementUtil.getElement(storeMenuLink).click();
        return new StorePage(driver);
    }
    public HomePage load()
    {
        load("/");
        return this;
    }
}
