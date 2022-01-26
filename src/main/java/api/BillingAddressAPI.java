package api;

import enums.EnvType;
import factories.EnvFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import objects.BillingAddress;
import objects.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

public class BillingAddressAPI {

    private Cookies cookies;

    public Cookies getCookies() {
        return cookies;
    }

    public String fetchBillingNonceValueUsingJsoup(Cookies cookies) {
        Response response = getAccount(cookies);
        Document document = Jsoup.parse(response.body().prettyPrint());
        Element element = document.selectFirst("#woocommerce-edit-address-nonce");
        System.out.println("element.attr  "+element.attr("value"));
        return element.attr("value");
    }

    public Response getAccount(Cookies cookies) {
        Response response = RestAssured.given()
                .baseUri(EnvFactory.getURL(EnvType.STAGE))
                .cookies(cookies)
                .log()
                .all()
                .when()
                .get("/account/edit-address/billing/")
                .then()
                .log()
                .all()
                .extract()
                .response();
        if (response.getStatusCode() != 200 && response.getStatusCode() != 302) {
            throw new RuntimeException("failed to fetch the account " + response.getStatusCode());
        }
        return response;

    }

    public Response saveAddress(Cookies cookies, BillingAddress billingAddress) {
        Map<String, String> formParameters = new HashMap<>();
        formParameters.put("billing_first_name",billingAddress.getFirstName());
        formParameters.put("billing_last_name", billingAddress.getLastName());
        formParameters.put("billing_company", "ADP");
        formParameters.put("billing_country", "IN");
        formParameters.put("billing_address_1", billingAddress.getStreetAddress());
        formParameters.put("billing_address_2", billingAddress.getStreetAddress());
        formParameters.put("billing_city",billingAddress.getCity());
        formParameters.put("billing_state", "MH");
        formParameters.put("billing_postcode", billingAddress.getPostalCode());
        formParameters.put("billing_phone", "88846881541");
        formParameters.put("billing_email", billingAddress.getEmail());
        formParameters.put("save_address", "Save address");
        formParameters.put("woocommerce-edit-address-nonce", fetchBillingNonceValueUsingJsoup(cookies));
        formParameters.put("action", "edit_address");

        Response response = RestAssured.given()
                .baseUri(EnvFactory.getURL(EnvType.STAGE))
                .cookies(cookies)
                .contentType(ContentType.URLENC)
                .formParams(formParameters)
                .log()
                .all()
                .when()
                .post("/account/edit-address/billing/")
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
}
