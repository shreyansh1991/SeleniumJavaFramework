package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends  BasePage{
    private final By productName=By.cssSelector("td[class='product-name'] a");
    private final By checkoutBtn=By.cssSelector(".checkout-button");
    private final By couponTextBox=By.id("coupon_code");
    private final By couppnBtn=By.name("apply_coupon");
    private final By totalPrice=By.xpath("//th[contains(text(),'Total')]/following::bdi");
    private final By overlay = By.cssSelector(".blockUI.blockOverlay");
    private final By freeShipingLabel=By.xpath("//label[contains(text(),'Free shipping')]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage load()
    {
        load("/checkout");

        return this;
    }

    @Override
    protected CartPage isLoaded() {
        return this;
    }

    public String getProductName()
    {
    return driver.findElement(productName).getText();
    }
    public CheckoutPage checkOut()
    {
       elementUtil.getElement(checkoutBtn).click();
        return new CheckoutPage(driver);
    }
    public CartPage applyCoupon(String couponName)
    {
        elementUtil.doSendKeys(couponTextBox,couponName);
        elementUtil.doClick(couppnBtn);
        waitForOverlaysToDisappear(overlay);
        return this;
    }
    public String getTotalAmount()
    {
        return elementUtil.doGetText(totalPrice);
    }
    public boolean isFreeShippingDisplayed()
    {
        return elementUtil.doIsDiplayed(freeShipingLabel);
    }
}
