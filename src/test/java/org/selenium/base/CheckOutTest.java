package org.selenium.base;

import api.AddToCart;
import api.BillingAddressAPI;
import api.SignupAPI;
import driver.DriverManager;
import io.restassured.response.Response;
import objects.BillingAddress;
import objects.Product;
import objects.User;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CheckoutPage;
import utils.CookieUtil;
import utils.FakerUtil;
import utils.JacksonUtils;

public class CheckOutTest extends BaseTest {
    //    @Test
    public void guestCheckOutUsingDirectBankTransfer() {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("billingAddress.json", BillingAddress.class);
        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver()).load();
        AddToCart addToCart = new AddToCart();
        Product product = new Product(1215);
        addToCart.addToCart(product.getId(), 1);
        new CookieUtil().injectCookiesToBrowser(addToCart.getCookies());

        checkoutPage.load()
                .setBillingAddress(billingAddress).
                selectDirectBankTransfer()
                .clickPlaceOrderButton();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.", "Assertion failed.");

    }

    //    @Test
    public void loginAndCheckOutUsingDirectBankTransfer() {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("billingAddress.json", BillingAddress.class);
        Long randomNumber = new FakerUtil().generateRandomNumber();
        User user = new User();
        user.setUsername("demouser " + randomNumber);
        user.setPassword("demopwd");
        user.setEmail("demouser" + randomNumber + "@gmail.com");
        SignupAPI signupAPI = new SignupAPI();
        Response response = signupAPI.registerUser(user);
        AddToCart addToCart = new AddToCart(signupAPI.getCookies());
        Product product = new Product(1215);
        addToCart.addToCart(product.getId(), 1);
        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver()).load();
        new CookieUtil().injectCookiesToBrowser(signupAPI.getCookies());
        // checkoutPage.load().clickHereToLogin().login(user.getUsername(), user.getPassword());
        //Assert.assertTrue(checkoutPage.getCheckOutText().trim().equals("Checkout"));
        checkoutPage.load();
        checkoutPage.setBillingAddress(billingAddress).
                selectDirectBankTransfer()
                .clickPlaceOrderButton();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.", "Assertion failed.");

    }

    @Test(dataProvider = "getAddress")
    public void loginAndCheckOutUsingDirectBankTransferUsingAPI(BillingAddress billingAddress) {
        Long randomNumber = new FakerUtil().generateRandomNumber();
        User user = new User();
        user.setUsername("demouser " + randomNumber);
        user.setPassword("demopwd");
        user.setEmail("demouser" + randomNumber + "@gmail.com");
        SignupAPI signupAPI = new SignupAPI();
        Response response = signupAPI.registerUser(user);
        AddToCart addToCart = new AddToCart(signupAPI.getCookies());
        Product product = new Product(1215);
        addToCart.addToCart(product.getId(), 1);
        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver()).load();

        BillingAddressAPI billingAddressAPI = new BillingAddressAPI();
        billingAddressAPI.saveAddress(signupAPI.getCookies(), billingAddress);
        CookieUtil.injectCookiesToBrowser(signupAPI.getCookies());
        checkoutPage.load();


        checkoutPage.
                selectDirectBankTransfer()
                .clickPlaceOrderButton();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.", "Assertion failed.");

    }

    @DataProvider(name = "getAddress",parallel = true)
    public Object[] getAddress() {
        return JacksonUtils.deserializeJson("billingAddress.json", BillingAddress[].class);
    }
}
