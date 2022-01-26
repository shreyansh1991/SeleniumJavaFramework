package org.selenium.base;

import driver.DriverManager;
import objects.Product;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.StorePage;

public class AddToCartTest extends BaseTest{
    @Test
    public void addToCartFromStorePage()
    {
        Product product = new Product(1215);
        CartPage cartPage = new StorePage(DriverManager.getDriver())
                .load()
                .clickAddToCartButton(product.getName())
                .clickViewCartLink();
        Assert.assertEquals(cartPage.getProductName(), product.getName());
    }

}
