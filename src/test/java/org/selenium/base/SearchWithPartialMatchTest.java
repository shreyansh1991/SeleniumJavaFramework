package org.selenium.base;

import driver.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.StorePage;

public class SearchWithPartialMatchTest extends BaseTest {

    @Test
    public void searchWithPartialMatch() {
        String searchFor = "Blue";
        StorePage storePage = new StorePage(DriverManager.getDriver())
                .load()
                .enterTextInSearchField(searchFor).clickSearchButton();
        Assert.assertEquals(storePage.waitForTitle(searchFor), "Search Results for “" + searchFor + "” – AskOmDch");
        Assert.assertEquals(storePage.getTitle(), "Search results: “Blue”");

    }
}
