package api;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import utils.FakerUtil;

import java.util.HashMap;
import java.util.Map;

public class OrangeHRM {
    private Cookies cookies;

    public OrangeHRM(Cookies cookies) {
        this.cookies = cookies;
    }

    public Response addUser() {
        Map<String, String> formParameters = new HashMap<>();

        formParameters.put("systemUser[userType]","2");
        formParameters.put("systemUser[employeeName][empName]","Garry White");
        formParameters.put("systemUser[employeeName][empId]","25");
        formParameters.put("systemUser[userName]", "Shreyansh"+new FakerUtil().generateRandomNumber());
        formParameters.put("systemUser[status]","1");
        formParameters.put("systemUser[password]", "password");
        formParameters.put("systemUser[confirmPassword]", "password");
        formParameters.put("systemUser[userId]", "");
        formParameters.put("systemUser[_csrf_token]", getToken());
        System.out.println(" 37 cookies "+this.cookies);
        Response response = RestAssured.given()
                .baseUri("https://opensource-demo.orangehrmlive.com")
                .config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .cookies(this.cookies)
                .contentType(ContentType.URLENC)
                .formParams(formParameters)
                .log()
                .all()
                .when()
                .post("/index.php/admin/saveSystemUser")
                .then()
                .log()
                .all()
                .extract()
                .response();
        System.out.println(" response.getStatusCode() " + response.getStatusCode());
        if (response.getStatusCode() != 302 && response.getStatusCode() != 200) {
            throw new RuntimeException("failed to fetch the account " + response.getStatusCode());
        }
        this.cookies = response.getDetailedCookies();
        System.out.println("Billing Cookie ---------------------> "+this.cookies);
        return response;

    }

    private String getToken() {
        Response response = RestAssured.given()
                .baseUri("https://opensource-demo.orangehrmlive.com")
                .cookies(this.cookies)
                .log()
                .all()
                .when()
                .get("/index.php/admin/viewSystemUsers")
                .then()
                .log()
                .all()
                .extract()
                .response();
        System.out.println(" response.getStatusCode() " + response.getStatusCode());
        if (response.getStatusCode() != 302 && response.getStatusCode() != 200) {
            throw new RuntimeException("failed to fetch the Token " + response.getStatusCode());
        }
        Document document = Jsoup.parse(response.body().prettyPrint());
        Element element = document.selectFirst("#actionValidatingForm__csrf_token");
        System.out.println("element.attr  "+element.attr("value"));
        return element.attr("value");

    }
}

