package pages;

import org.bouncycastle.util.Store;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StorePage extends BasePage {
    private final By searchField = By.id("woocommerce-product-search-field-0");
    private final By searchButton = By.cssSelector("button[value='Search']");
    private final By title = By.cssSelector(".woocommerce-products-header__title.page-title");
    private final By viewCartLink = By.cssSelector("a[title='View cart']");

    public StorePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected StorePage isLoaded() {
        return this;
    }

    public StorePage enterTextInSearchField(String text) {
        elementUtil.getElement(searchField).sendKeys(text);
        return this;
    }

    public StorePage load() {
        load("/store");
        return this;
    }

    public StorePage clickSearchButton() {
        driver.findElement(searchButton).click();
        return this;
    }
    public String waitForTitle(String searchTerm)
    {
        return elementUtil.waitForTitleContains(10, "Search Results for “"+searchTerm+"” – AskOmDch");
    }

    private By getAddToCartBtn(String productName) {
        return By.cssSelector("a[aria-label='Add “" + productName + "” to your cart']");
    }

    public StorePage clickAddToCartButton(String productName) {
        By addToCartBtn = getAddToCartBtn(productName);
        driver.findElement(addToCartBtn).click();
        return this;
    }

    public String getTitle() {
        return driver.findElement(title).getText();
    }

    public CartPage clickViewCartLink() {
//         wait.until(ExpectedConditions.visibilityOf(driver.findElement(viewCartLink))); // not working and it seems because viewCartLink gets available in DOM after few seconds. and implicitly wait is set to 0.
        wait.until(d -> driver.findElement(viewCartLink).isDisplayed()); // working even when implicitly wait is 0
        driver.findElement(viewCartLink).click();
        return new CartPage(driver);
    }
}
