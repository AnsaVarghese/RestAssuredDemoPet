package com.apirestassured.teststeps;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import io.restassured.RestAssured;

public class ApiGiven {

	public void SimpleGETRequest() { // SIMPLE GET REQUEST

		RestAssured.get(
				"https://petstore.swagger.io/v2/pet/findByStatus?status=available")
				.then().log().body();

	}
	@Test
	public void GETRequest() { // find pet by status

		RestAssured.given().baseUri("https://petstore.swagger.io")
				.get("/v2/pet/findByStatus?status=available").then().log()
				.body();

	}
	@Test
	public void GetQueryParam() { // find pet by status
		// AVAILABLE WITH QUERYPARAM

		RestAssured.given().baseUri("https://petstore.swagger.io")
				.queryParam("status", "sold").when().get("/v2/pet/findByStatus")
				.then().log().body();

	}

	public void GetQueryMultivalueParam() { // Find pet by status
		// AVAILABLE WITH QUERYPARAM

		List<String> values = new ArrayList<String>();
		values.add("available");
		values.add("sold");

		RestAssured.given().baseUri("https://petstore.swagger.io")
				.param("status", values).when().get("/v2/pet/findByStatus")
				.then().log().body();
		System.out.println("--END OF FIRST TEST--");

		RestAssured.given().baseUri("https://petstore.swagger.io")
				.queryParam("status", "available", "sold").when()
				.get("/v2/pet/findByStatus").then().log().body();

		RestAssured.given().baseUri("https://petstore.swagger.io")
				.queryParam("status", "available").queryParam("status", "sold")
				.when().get("/v2/pet/findByStatus").then().log().body();

	}

	public void GetQueryPathParam() { // Find By pet id
		RestAssured.given().get("https://petstore.swagger.io/v2/pet/1").then()
				.log().body();
	}

	public void Getwithheader() { // Find By pet id
		RestAssured.given().accept("application/Json")
				.get("https://petstore.swagger.io/v2/pet/1").then().log()
				.body();
	}

}
