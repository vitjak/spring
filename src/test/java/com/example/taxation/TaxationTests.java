package com.example.taxation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.hamcrest.Matchers;
import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest
public class TaxationTests {

    @Before
    public void setupURL() {
        // here we setup the default URL and API base path to use throughout the tests
        RestAssured.baseURI = "http://localhost:8080/";
        RestAssured.basePath = "/taxes/";
    }

    @Test
    public void generalTaxationTest() {
        final String body = "{\"traderId\": 1, \"playedAmount\": 5, \"odd\": 3.2}";

        final ValidatableResponse response = given().contentType(ContentType.JSON)
                .when()
                .body(body)
                .post("/general")
                .then();

        response.statusCode(200);

        final Response jsonResponse = response
                .contentType(ContentType.JSON)
                .extract().response();

        String befTax = jsonResponse.path("possibleReturnAmountBefTax");
        String afterTax = jsonResponse.path("possibleReturnAmountAfterTax");
        String taxRateChar = jsonResponse.path("taxRate");
        String taxAmountChar = jsonResponse.path("taxAmount");

        final BigDecimal possibleReturnAmountBefTax = new BigDecimal(befTax);
        final BigDecimal possibleReturnAmountAfterTax = new BigDecimal(afterTax);
        final BigDecimal taxRate = new BigDecimal(taxRateChar);
        final BigDecimal taxAmount = new BigDecimal(taxAmountChar);

        assertThat(BigDecimal.valueOf(16.0), Matchers.comparesEqualTo(possibleReturnAmountBefTax));
        assertThat(BigDecimal.valueOf(14.0), Matchers.comparesEqualTo(possibleReturnAmountAfterTax));
        assertThat(BigDecimal.valueOf(0.125), Matchers.comparesEqualTo(taxRate));
        assertThat(BigDecimal.valueOf(2), Matchers.comparesEqualTo(taxAmount));
    }

    @Test
    public void winningsTaxationTest() {
        final String body = "{\"traderId\": 1, \"playedAmount\": 15, \"odd\": 3.2}";

        final ValidatableResponse response = given().contentType(ContentType.JSON)
                .when()
                .body(body)
                .post("/winnings")
                .then();

        response.statusCode(200);

        final Response jsonResponse = response
                .contentType(ContentType.JSON)
                .extract().response();

        String befTax = jsonResponse.path("possibleReturnAmountBefTax");
        String afterTax = jsonResponse.path("possibleReturnAmountAfterTax");
        String taxRateChar = jsonResponse.path("taxRate");
        String taxAmountChar = jsonResponse.path("taxAmount");

        final BigDecimal possibleReturnAmountBefTax = new BigDecimal(befTax);
        final BigDecimal possibleReturnAmountAfterTax = new BigDecimal(afterTax);
        final BigDecimal taxRate = new BigDecimal(taxRateChar);
        final BigDecimal taxAmount = new BigDecimal(taxAmountChar);

        assertThat(BigDecimal.valueOf(48.0), Matchers.comparesEqualTo(possibleReturnAmountBefTax));
        assertThat(BigDecimal.valueOf(43.2), Matchers.comparesEqualTo(possibleReturnAmountAfterTax));
        assertThat(BigDecimal.valueOf(0.1), Matchers.comparesEqualTo(taxRate));
        assertThat(BigDecimal.valueOf(4.8), Matchers.comparesEqualTo(taxAmount));
    }
}