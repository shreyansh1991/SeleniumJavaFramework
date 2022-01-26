package org.selenium.base.orangehrm;

import additionalapiactions.OrangeHRMLLoginAPI;
import additionalapiactions.OrangeHrmAPI;
import api.OrangeHRM;
import driver.DriverManager;
import org.selenium.base.BaseTest;
import org.testng.annotations.Test;
import utils.CookieUtil;

public class OrangeHrmAPITest extends BaseTest {
    @Test
    public void OrangeHRMLLoginAPI() {
        OrangeHRMLLoginAPI orangeHRMLLoginAPI = new OrangeHRMLLoginAPI();
        orangeHRMLLoginAPI.login();
        DriverManager.getDriver().get("https://opensource-demo.orangehrmlive.com");
        CookieUtil.injectCookiesToBrowser(orangeHRMLLoginAPI.getCookies());
        DriverManager.getDriver().get("https://opensource-demo.orangehrmlive.com");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CookieUtil.injectCookiesToBrowser(orangeHRMLLoginAPI.getCookies());
        DriverManager.getDriver().get("https://opensource-demo.orangehrmlive.com/index.php/dashboard");
        System.out.println("cookies " + orangeHRMLLoginAPI.getCookies());
        DriverManager.getDriver().get("https://opensource-demo.orangehrmlive.com/index.php/admin/viewSystemUsers");
        OrangeHRM orangeHrmAPI = new OrangeHRM(orangeHRMLLoginAPI.getCookies());
        orangeHrmAPI.addUser();
        DriverManager.getDriver().get("https://opensource-demo.orangehrmlive.com/index.php/admin/viewSystemUsers");

        orangeHRMLLoginAPI.logout();


    }
}
