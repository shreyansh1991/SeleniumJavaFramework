package org.selenium.base.assignments;

import api.AddToCart;
import api.BillingAddressAPI;
import api.SignupAPI;
import driver.DriverManager;
import io.restassured.response.Response;
import objects.BillingAddress;
import objects.Product;
import objects.User;
import org.openqa.selenium.By;
import org.selenium.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CheckoutPage;
import utils.CookieUtil;
import utils.FakerUtil;
import utils.JacksonUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Assignment2Test extends BaseTest {
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

    @DataProvider(name = "getAddress")
    public Object[] getAddress() {
        return JacksonUtils.deserializeJson("singleAddress.json", BillingAddress[].class);
    }
    private String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d,yyyy");
        LocalDate now = LocalDate.now();
        String date = dtf.format(now);
        String todaysDate = dtf.format(now);
        return todaysDate;
    }
}
