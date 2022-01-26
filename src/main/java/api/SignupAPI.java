package api;

import enums.EnvType;
import factories.EnvFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import objects.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

public class SignupAPI {

    private Cookies cookies;

    public Cookies getCookies() {
        return cookies;
    }
    public String fetchRegisterNonceValueUsingJsoup()
    {
        Response response = getAccount();
        Document document = Jsoup.parse(response.body().prettyPrint());
        Element element = document.selectFirst("#woocommerce-register-nonce");
        return element.attr("value");
    }
    public Response getAccount() {
        Cookies cookies = new Cookies();
        Response response = RestAssured.given()
                .baseUri(EnvFactory.getURL(EnvType.STAGE))
                .cookies(cookies)
                .log()
                .all()
                .when()
                .get("/account")
                .then()
                .log()
                .all()
                .extract()
                .response();
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("failed to fetch the account " + response.getStatusCode());
        }
        return response;

    }
    public Response registerUser(User user) {
        Cookies cookies = new Cookies();
        Map<String,String> formParameters=new HashMap<>();
        formParameters.put("username", user.getUsername());
        formParameters.put("email",user.getEmail());
        formParameters.put("password",user.getPassword());
        formParameters.put("woocommerce-register-nonce",fetchRegisterNonceValueUsingJsoup());
        formParameters.put("register","Register");
        Response response = RestAssured.given()
                .baseUri(EnvFactory.getURL(EnvType.STAGE))
                .cookies(cookies)
                .contentType(ContentType.URLENC)
                .formParams(formParameters)
                .log()
                .all()
                .when()
                .post("/account")
                .then()
                .log()
                .all()
                .extract()
                .response();
        System.out.println(" response.getStatusCode() "+response.getStatusCode());
        if (response.getStatusCode() != 302 && response.getStatusCode() != 200) {
            throw new RuntimeException("failed to fetch the account " + response.getStatusCode());
        }
        this.cookies = response.getDetailedCookies();
        return response;

    }
}
