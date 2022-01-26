package pages;

import enums.WaitStrategy;
import objects.BillingAddress;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.sql.SQLOutput;

public class CheckoutPage extends BasePage {
    private final By firstNameField = By.id("billing_first_name");
    private final By lastNameField = By.id("billing_last_name");
    private final By addressLineOneField = By.id("billing_address_1");
    private final By billingCityField = By.id("billing_city");
    private final By billingPostcodeField = By.id("billing_postcode");
    private final By billingEmailField = By.id("billing_email");
    private final By placeOrderButton = By.id("place_order");
    private final By successNotice = By.cssSelector(".woocommerce-notice");


    private final By clickHereToLoginLink = By.cssSelector(".showlogin");
    private final By userNameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.name("login");
    private final By overlay = By.cssSelector(".blockUI.blockOverlay");

    private final By countryDropdown = By.id("billing_country");
    private final By streetField = By.id("billing_address_1");
    private final By stateDropdown = By.id("billing_state");
    private final By alternateStateDropdown = By.id("select2-billing_state-container");

    private final By directBankTransferPayment = By.id("payment_method_bacs");
    private final By alternateCountryDropdown = By.id("select2-billing_country-container");
    private final By checkoutText = By.cssSelector(".has-text-align-center");


    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage load() {
        load("/checkout/");
        return this;
    }

    @Override
    protected CheckoutPage isLoaded() {
        return this;
    }

    public CheckoutPage enterFirstName(String text) {
        driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys(text);
        return this;
    }

    public CheckoutPage enterLastName(String text) {
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(text);
        return this;
    }

    public CheckoutPage enterAddressLine(String text) {
        driver.findElement(addressLineOneField).clear();
        driver.findElement(addressLineOneField).sendKeys(text);
        return this;
    }

    public CheckoutPage enterCity(String text) {
        driver.findElement(billingCityField).clear();
        driver.findElement(billingCityField).sendKeys(text);
        return this;
    }

    public CheckoutPage enterPostCode(String text) {
        driver.findElement(billingPostcodeField).clear();
        driver.findElement(billingPostcodeField).sendKeys(text);
        return this;
    }

    public CheckoutPage enterEmail(String text) {
        driver.findElement(billingEmailField).clear();
        driver.findElement(billingEmailField).sendKeys(text);
        return this;
    }

    public CheckoutPage clickPlaceOrderButton() {
        waitForOverlaysToDisappear(overlay);
        driver.findElement(placeOrderButton).click();
        return this;
    }

    public String getNotice() {
        waitForOverlaysToDisappear(overlay);
        return driver.findElement(successNotice).getText();
    }

    public CheckoutPage clickHereToLogin() {
        driver.findElement(clickHereToLoginLink).click();
        return this;
    }

    public CheckoutPage enterUsername(String username) {
        driver.findElement(userNameField).sendKeys(username);
        return this;
    }

    public CheckoutPage enterPassword(String pwd) {
        driver.findElement(passwordField).sendKeys(pwd);
        return this;
    }

    public CheckoutPage clickLoginButton() {
        driver.findElement(loginButton).click();
        return this;
    }

    public CheckoutPage login(String username, String password) {
        wait.until(d -> driver.findElement(userNameField).isDisplayed());
        return enterUsername(username).
                enterPassword(password).clickLoginButton();
    }

    public CheckoutPage setBillingAddress(BillingAddress billingAddress) {
        enterFirstName(billingAddress.getFirstName())
                .enterLastName(billingAddress.getLastName())
                .selectCountry(billingAddress.getCountry())
//                .waitForOverlaysToDisappear(overlay);
                .selectStreet(billingAddress.getStreetAddress())
                .enterCity(billingAddress.getCity())
                .selectState(billingAddress.getState())
                .enterPostCode(billingAddress.getPostalCode())
                .enterEmail(billingAddress.getEmail());
        return this;
    }

    public CheckoutPage selectCountry(String countryName) {
        elementUtil.waitForElementToClickable(alternateCountryDropdown, 30).click();
        WebElement webElement = elementUtil.waitForElementToClickable(By.xpath("//li[text()='" + countryName + "']"), 30);
        jsUtil.scrollIntoView(webElement);
        webElement.click();
        return this;
    }

    public CheckoutPage selectState(String state) {
        elementUtil.waitForElementToClickable(alternateStateDropdown, 30).click();
        WebElement webElement = elementUtil.waitForElementToClickable(By.xpath("//li[text()='" + state + "']"), 30);
        jsUtil.scrollIntoView(webElement);
        webElement.click();
        return this;
    }

    public CheckoutPage selectStreet(String stateName) {
        sendKeys(streetField, stateName, WaitStrategy.VISIBLE, "State Field");
        return this;
    }

    public CheckoutPage selectDirectBankTransfer() {
        WebElement webElement = elementUtil.waitForElementToClickable(directBankTransferPayment, 10);
        if (!webElement.isSelected()) {
            webElement.click();
        }
        return this;
    }

    public String getCheckOutText() {
       return elementUtil.doGetText(checkoutText);
    }
}
