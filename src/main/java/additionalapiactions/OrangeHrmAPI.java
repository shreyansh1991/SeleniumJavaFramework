package additionalapiactions;

import utils.CookieUtil;

public class OrangeHrmAPI {
    public static void main(String[] args) {
        OrangeHRMLLoginAPI orangeHRMLLoginAPI = new OrangeHRMLLoginAPI();
        orangeHRMLLoginAPI.login();
        new CookieUtil().injectCookiesToBrowser(orangeHRMLLoginAPI.getCookies());


    }
}
