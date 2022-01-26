package api;

import enums.EnvType;
import factories.EnvFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class AddToCart {
    private Cookies cookies;

    public AddToCart() {
    }

    public AddToCart(Cookies cookies) {
        this.cookies = cookies;
    }

    public Response addToCart(int productID, int quantity) {
        if (cookies == null)
            cookies = new Cookies();
        Map<String, Object> formParameters = new HashMap<>();
        formParameters.put("product_sku", "");
        formParameters.put("product_id",productID);
        formParameters.put("quantity",quantity);

        Response response = RestAssured.given()
                .baseUri(EnvFactory.getURL(EnvType.STAGE))
                .cookies(cookies)
                .contentType(ContentType.URLENC)
                .formParams(formParameters)
                .log()
                .all()
                .when()
                .post("/?wc-ajax=add_to_cart")
                .then()
                .log()
                .all()
                .extract()
                .response();
        System.out.println(" response.getStatusCode() " + response.getStatusCode());
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("failed to fetch the account " + response.getStatusCode());
        }
        this.cookies = response.getDetailedCookies();
        return response;
    }
    public Cookies getCookies() {
        return cookies;
    }
}
