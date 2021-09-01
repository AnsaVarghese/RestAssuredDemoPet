package com.apirestassured.teststeps;

import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class ApiGETMethodandResponse {

	public RequestSpecification getRequestSpecification() { // getRequestSpecification

		RequestSpecification requestSpecification = RestAssured.given()
				.baseUri("https://petstore.swagger.io");
		return requestSpecification;
	}

	// -----BEGIN GET REQUEST with response statuscode

	public void GETRequestwithResponse() { // SIMPLE GET REQUEST WITH RESPONSE
											// STATUS CODE
											// //find By petstatus

		Response response = getRequestSpecification().when()
				.get("/v2/pet/findByStatus?status=available");
		int statuscode = response.getStatusCode();

		Assert.assertEquals(200, statuscode);

	}

	public void GETRequestThenResponse() { // SIMPLE GET REQUEST WITH RESPONSE
											// //find By petstatus

		getRequestSpecification().when()
				.get("/v2/pet/findByStatus?status=available").then()
				.statusCode(200);

		getRequestSpecification().when().get("/v2/pet/findByStatus?status=sold")
				.then().assertThat().statusCode(equalTo(200));

	}

	// -----BEGIN GET REQUEST FOR Headers, Content Type

	public void GetHeaders() { // Get Headers

		Response response = getRequestSpecification().when()
				.get("/v2/pet/findByStatus?status=available");
		System.out.println(response.headers());
		System.out.println("--END OF TEST--");

	}

	public void GetContentType() { // Get ContentType

		ValidatableResponse response = RestAssured.given().get(
				"https://petstore.swagger.io/v2/pet/findByStatus?status=available")
				.then().statusCode(200);
		System.out.println(
				"Content type is--- " + response.extract().contentType());
		System.out.println("--END OF TEST--");

	}

	public void ContentTypeAssert() {
		RestAssured.given().get(
				"https://petstore.swagger.io/v2/pet/findByStatus?status=available")
				.then().assertThat().contentType("text");
	}

	public void GetResponseTime() { // Get ResponseTime

		Response response = getRequestSpecification().when()
				.get("/v2/pet/findByStatus?status=available");
		System.out.println("--GET TIME--");
		System.out.println(response.getTime());
		System.out.println("--GET TIME IN MILLISECONDS--");
		System.out.println(response.getTimeIn(TimeUnit.MILLISECONDS));
		System.out.println("--GET TIME IN SECONDS--");
		System.out.println(response.getTimeIn(TimeUnit.SECONDS));
		System.out.println("--GET TIME IN MInS--");
		System.out.println(response.getTimeIn(TimeUnit.MINUTES));

	}

	// -----BEGIN find pets by status Extract response body

	public void GETWITHResponseBody() {

		Response response = getRequestSpecification().when()
				.get("/v2/pet/findByStatus?status=sold").then().statusCode(200)
				.extract().response();

		System.out.println(response.getBody().asString());
		System.out.println("--END OF TEST--");

	}

	public void GetResponseBodywithQueryParam() { // with multiple queryParams

		Response response = getRequestSpecification()
				.queryParam("status", "available").queryParam("status", "sold")
				.when().get("/v2/pet/findByStatus").then().statusCode(200)
				.extract().response();

		System.out.println(response.getBody().asString());

		System.out.println("--END OF TEST--");

	}

	// -----BEGIN find pets by id--- Extract Response with JSONPATH

	public void GetJsonPath() { // Find By pet id
		JsonPath responseJson = getRequestSpecification()
				.contentType("application/json").when().get("/v2/pet/2356")
				.then().statusCode(200).extract().jsonPath();

		// JsonPath responseJson = RestAssured.given()
		// .get("https://petstore.swagger.io/v2/pet/2356").then()
		// .statusCode(200).extract().jsonPath();

		System.out.println("Pet Id=" + responseJson.getInt("id"));
		System.out.println("Pet Name=" + responseJson.getString("name"));
		System.out.println(
				"Category =" + responseJson.getString("category.name"));
		Assert.assertEquals(responseJson.getString("name"), "rabbit");

	}

	// -----BEGIN find pets by status--- Extract Response with JSONPATH
	@Test
	public void GetJsonPathForFindByStatus() { // PRINT ALL AVAILABLE PET IDs
		System.out.println("--BEGIN OF TEST--");

		JsonPath responseJson = getRequestSpecification()
				.contentType("application/json").when()
				.get("/v2/pet/findByStatus?status=available").then()
				.statusCode(200).extract().jsonPath();

		List<Map<String, Object>> Result = responseJson.get();

		System.out.println(Result.size());
		for (Map<String, Object> data1 : Result) {
			System.out.println(data1.get("id"));
		}

		System.out.println("--END OF TEST--");

	};

	public void JsonPathFindNameoffirstpetSold() {

		System.out.println("--BEGIN OF TEST--");

		JsonPath responseJson = getRequestSpecification()
				.contentType("application/json").when()
				.get("/v2/pet/findByStatus?status=sold").then().statusCode(200)
				.extract().jsonPath();

		System.out.println(responseJson.get()); // print the data with JsonPath

		List<Map<String, String>> Result = responseJson.get(); // Parsing JSON
																// ArrayList and
																// HashMap
		System.out.println("Size of array---" + Result.size());
		System.out.println("First pet details is---" + Result.get(0));

		System.out.println("Name of first pet =" + Result.get(0).get("name"));
		String id = String.valueOf((Result.get(0).get("id")));
		System.out.println("First pet id =" + id);
		System.out.println("Name of first pet by searching with id = "
				+ responseJson.get("findAll{it.id==" + id + "}.name"));
		System.out.println("--END OF TEST--");

	}

}
