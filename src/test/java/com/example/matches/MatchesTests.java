package com.example.matches;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
public class MatchesTests {

    @Before
    public void setupURL() {
        // here we setup the default URL and API base path to use throughout the tests
        RestAssured.baseURI = "http://localhost:8080/";
        RestAssured.basePath = "/matches/";
    }

    @Test
    public void importTest() {
        final ValidatableResponse response = given().contentType(ContentType.JSON)
                .when()
                .post("/import")
                .then();

        response.statusCode(200);
    }
}