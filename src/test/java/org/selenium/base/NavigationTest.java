package org.selenium.base;

import driver.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.StorePage;

public class NavigationTest extends BaseTest{
    @Test
    public void navigateFromHomeToStoreUsingMainMenu()
    {
        StorePage storePage = new HomePage(DriverManager.getDriver()).
                load().
                isLoaded().
                navigateToStoreUsingMenu();
        Assert.assertEquals(storePage.getTitle(), "Store");
        System.out.println("Passed.");
    }
}
