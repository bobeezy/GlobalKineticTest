package main.java.api;

/**
 * @author lionel.mangoua
 * date: 07/07/20
 */

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import main.java.Engine.DriverFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class APICommonMethods extends DriverFactory {

    public static String getValueFromJson(ValidatableResponse response, String jsonPath) {

        String value = response.extract().jsonPath().getString(jsonPath);
        value = value.replaceAll("\\[", "").replaceAll("\\]", "");
        return value;
    }

    //region <GET method>
    public ValidatableResponse getMethod(String endpoint, Map header) {

        log("Method: GET\n---------------- URL ------------------\n"
                + RestAssured.baseURI + "" + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        response =
                given().
                        spec(requestSpec).
                        headers(header).
                when().
                        get(endpoint).
                then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }
    //endregion

    //region <POST method>
    public ValidatableResponse postMethod(String payload, String endpoint, Map header) {

        log("Method: POST\n---------------- URL ------------------\n"
                + RestAssured.baseURI + "" + RestAssured.basePath + endpoint, "INFO", "text");

        log("--------------- HEADERS ---------------\n" + header, "INFO", "text");

        log("--------------- REQUEST ---------------\n", "INFO", "text");
        log(payload, "INFO", "json");

        response =
                given().
                        spec(requestSpec).
                        headers(header).
                        body(payload).
                when().
                        post(endpoint).
                then();

        log("--------------- RESPONSE ---------------\n", "INFO", "text");
        log(response.extract().body().asString(), "INFO", "json");

        return response;
    }
    //endregion
}
