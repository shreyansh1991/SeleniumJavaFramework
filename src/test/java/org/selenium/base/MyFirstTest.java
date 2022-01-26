package org.selenium.base;

import driver.DriverManager;
import objects.BillingAddress;
import objects.Product;
import objects.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.StorePage;
import utils.JacksonUtils;

// We should avoid the how part in the test case.
public class MyFirstTest extends BaseTest {
//    @Test
    public void guestCheckOutUsingDirectBankTransfer() {
        String searchFor = "Blue";

        StorePage storePage = new HomePage(DriverManager.getDriver()).
                load().
                isLoaded().
                navigateToStoreUsingMenu()
                .enterTextInSearchField(searchFor).
                        clickSearchButton();
        Assert.assertEquals(storePage.getTitle(), "Search results: “Blue”");
        Product product = new Product(1215);
        storePage.clickAddToCartButton(product.getName());
        CartPage cartPage = storePage.clickViewCartLink();
        Assert.assertEquals(cartPage.getProductName(), product.getName());
        BillingAddress billingAddress = JacksonUtils.deserializeJson("billingAddress.json", BillingAddress.class);

        CheckoutPage checkoutPage = cartPage.checkOut()
                .setBillingAddress(billingAddress).
                        selectDirectBankTransfer()
                .clickPlaceOrderButton();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.", "Assertion failed.");

    }

    @Test
    public void loginAndCheckoutUsingDirectBankTransfer() {
        String searchFor = "Blue";

        StorePage storePage = new HomePage(DriverManager.getDriver()).
                load().isLoaded().navigateToStoreUsingMenu()
                .enterTextInSearchField(searchFor).
                        clickSearchButton();
        Assert.assertEquals(storePage.getTitle(), "Search results: “Blue”");
        Product product = new Product(1215);
        storePage.clickAddToCartButton(product.getName());

        CartPage cartPage = storePage.clickViewCartLink();
        Assert.assertEquals(cartPage.getProductName(), product.getName());

        User user = JacksonUtils.deserializeJson("user.json", User.class);
        BillingAddress billingAddress = JacksonUtils.deserializeJson("billingAddress.json", BillingAddress.class);
        CheckoutPage checkoutPage = cartPage.checkOut().clickHereToLogin().login(user.getUsername(), user.getPassword())
                .setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
                .clickPlaceOrderButton();

        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.", "Assertion failed.");

    }
}
