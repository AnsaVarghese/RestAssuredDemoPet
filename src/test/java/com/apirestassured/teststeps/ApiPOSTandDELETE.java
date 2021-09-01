package com.apirestassured.teststeps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiPOSTandDELETE {

	public RequestSpecification getRequestSpecification() { // getRequestSpecification

		RequestSpecification requestSpecification = RestAssured.given()
				.baseUri("https://petstore.swagger.io");
		return requestSpecification;
	}

	public void FirstTestPOST() { // PUT and POST
		System.out.println("--BEGIN OF TEST--");
		JSONObject category = new JSONObject();
		category.put("id", 21);
		category.put("name", "biganimal");
		JSONArray photoUrls = new JSONArray();
		photoUrls.put("string");
		JSONArray tags = new JSONArray();
		JSONObject tags1 = new JSONObject();
		tags1.put("id", 0);
		tags1.put("name", "string");
		tags.put(tags1);

		JSONObject requestBody = new JSONObject();
		requestBody.put("id", 2390);
		requestBody.put("category", category);
		requestBody.put("name", "bear");
		requestBody.put("photoUrls", photoUrls);
		requestBody.put("tags", tags);
		requestBody.put("status", "available");
		System.out.println(requestBody.toString());
		getRequestSpecification().contentType("application/json")
				.body(requestBody.toString()).when().post("/v2/pet").then()
				.statusCode(200);

		System.out.println("--END OF TEST--");

	};

	public void FirstTestPOSTwithJSONfile() throws IOException { // POST with
																	// JSON file

		String requestJson = generateStringFromResource(
				"C:\\TEMP\\Workspace\\TEMP Workspace\\APIRESTASSURED\\src\\test\\resources\\input\\Addfile.json"); // should
																													// edit
																													// the
																													// json
																													// file
																													// and
																													// give
																													// new
																													// id
																													// and
																													// names

		RestAssured.given().contentType("application/json").body(requestJson)
				.when().post("https://petstore.swagger.io/v2/pet").then()
				.statusCode(200);

		// extract response to get the details of pet added
		Response response = RestAssured
				.get("https://petstore.swagger.io/v2/pet/2359").then()
				.statusCode(200).extract().response();
		System.out.println("Pet Added =" + response.getBody().asString());

		// extract JsonPath response to get the details of pet added -print the
		// name of pet added ...mention the id as the pet id mention on Addfile

		JsonPath responseJson = RestAssured.given().when().get(
				"https://petstore.swagger.io/v2/pet/findByStatus?status=available")
				.then().statusCode(200).extract().jsonPath();
		String id = "2226";
		System.out.println(responseJson.get("findAll{it.id==" + id + "}.name"));

	}

	public static String generateStringFromResource(String path) // https://stackoverflow.com/questions/36579155/using-a-json-file-in-rest-assured-for-payload
			throws IOException { // to read from the json and send that as a
									// string

		return new String(Files.readAllBytes(Paths.get(path)));
	}

	public void FirstTestDELETE() {
		RestAssured.given().contentType("application/json").when()
				.delete("https://petstore.swagger.io/v2/pet/2359").then()
				.statusCode(200);
	}
	@Test
	public void POSTwithFORMParam() // Update a pet in the store with formdata
									// ---- need to enter an existing petid on
									// post method
	{
		getRequestSpecification()
				.contentType("application/x-www-form-urlencoded")
				.formParam("name", "newname1").formParam("status", "available")
				.when().post("/v2/pet/2390").then().statusCode(200);
	}

	public void FirstTestPUTwithJSONfile() throws IOException { // PUT with
		// JSON file

		String requestJson = generateStringFromResource(
				"C:\\TEMP\\Workspace\\TEMP Workspace\\APIRESTASSURED\\src\\test\\resources\\input\\Update.json");

		RestAssured.given().contentType("application/json").body(requestJson)
				.when().put("https://petstore.swagger.io/v2/pet").then()
				.statusCode(200);
	}

}
