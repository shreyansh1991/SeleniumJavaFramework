package org.selenium.base.assignments;

import driver.DriverManager;
import objects.Product;
import org.selenium.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.StorePage;

public class AddToCartTest extends BaseTest {
    @Test
    public void addToCartFromStorePage()
    {
        Product product = new Product(1215);
        CartPage cartPage = new StorePage(DriverManager.getDriver())
                .load()
                .clickAddToCartButton(product.getName())
                .clickViewCartLink();
        Assert.assertEquals(cartPage.getProductName(), product.getName());
        String totalAmount = cartPage.getTotalAmount();
        System.out.println("Before :: "+ totalAmount);
        cartPage.applyCoupon("freeship");
        totalAmount = cartPage.getTotalAmount();
        System.out.println("After :: "+ totalAmount);
        Assert.assertTrue(cartPage.isFreeShippingDisplayed(),"Free Shipping Label Missing..");


    }

}
