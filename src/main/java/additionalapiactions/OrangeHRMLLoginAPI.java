package additionalapiactions;

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

public class OrangeHRMLLoginAPI {

    private Cookies cookies;

    public Cookies getCookies() {
        return cookies;
    }
    public String fetchCSRFTokenValueUsingJsoup()
    {
        Response response = getAccount();
        Document document = Jsoup.parse(response.body().prettyPrint());
        Element element = document.selectFirst("input#csrf_token");
        System.out.println("element attr -- "+ element.attr("value"));
        return element.attr("value");
    }
    public Response getAccount() {
        Cookies cookies = new Cookies();
        Response response = RestAssured.given()
//                .baseUri(EnvFactory.getURL(EnvType.STAGE))
                .baseUri("https://opensource-demo.orangehrmlive.com/")
                .cookies(cookies)
                .log()
                .all()
                .when()
                .get()
                .then()
                .log()
                .all()
                .extract()
                .response();
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("failed to fetch the response " + response.getStatusCode());
        }
       this.cookies=response.getDetailedCookies();
        System.out.println("Cookies from login api are "+cookies);
        return response;

    }
    public Response login() {
        Map<String,String> formParameters=new HashMap<>();

        formParameters.put("_csrf_token", fetchCSRFTokenValueUsingJsoup());
        formParameters.put("txtUsername", "Admin");
        formParameters.put("txtPassword","admin123");
        formParameters.put("submit","LOGIN");
        Response response = RestAssured.given()
//                .baseUri(EnvFactory.getURL(EnvType.STAGE))
                .baseUri("https://opensource-demo.orangehrmlive.com")
                .cookies(getCookies())
                .contentType(ContentType.URLENC)
                .formParams(formParameters)
                .log()
                .all()
                .when()
                .post("/index.php/auth/validateCredentials")
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
        System.out.println("cookies --"+this.cookies);
        return response;

    }

    public void logout()
    {
        Response response = RestAssured.given()
                .baseUri("https://opensource-demo.orangehrmlive.com")
                .cookies(getCookies())
                .log()
                .all()
                .when()
                .post("/index.php/auth/logout")
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
        System.out.println("Log out Cookies --"+this.cookies);
    }
}

