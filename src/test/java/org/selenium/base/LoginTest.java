package org.selenium.base;

import api.AddToCart;
import api.SignupAPI;
import driver.DriverManager;
import io.restassured.response.Response;
import objects.Product;
import objects.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CheckoutPage;
import utils.CookieUtil;
import utils.FakerUtil;

public class LoginTest extends BaseTest {

    @Test
    public void loginDuringCheckout() {
        Long randomNumber = new FakerUtil().generateRandomNumber();
        User user = new User();
        user.setUsername("demouser " + randomNumber);
        user.setPassword("demopwd");
        user.setEmail("demouser" + randomNumber + "@gmail.com");
        SignupAPI signupAPI = new SignupAPI();
        Response response = signupAPI.registerUser(user);
        AddToCart addToCart = new AddToCart();
        Product product = new Product(1215);
        addToCart.addToCart(product.getId(), 1);
        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver()).load();
        CookieUtil.injectCookiesToBrowser(addToCart.getCookies());
        checkoutPage.load().clickHereToLogin().login(user.getUsername(), user.getPassword());
        Assert.assertTrue(checkoutPage.getCheckOutText().trim().equals("Checkout"));
    }

}
