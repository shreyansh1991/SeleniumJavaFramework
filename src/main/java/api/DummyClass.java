package api;

import io.restassured.response.Response;
import objects.User;
import utils.FakerUtil;

public class DummyClass {
    public static void main(String[] args) {
        String userName = "demouser" + new FakerUtil().generateRandomNumber();
        User user = new User();
        user.setUsername(userName);
        user.setPassword("demopwd");
        user.setEmail(userName + "@gmail.com");
        SignupAPI signupAPI = new SignupAPI();
        Response response = signupAPI.registerUser(user);
        System.out.println("signupAPI.getCookies() " + signupAPI.getCookies());
        AddToCart addToCart = new AddToCart(signupAPI.getCookies());
       addToCart.addToCart(1198, 1);
        System.out.println("cookies "+ addToCart.getCookies());
    }
}
