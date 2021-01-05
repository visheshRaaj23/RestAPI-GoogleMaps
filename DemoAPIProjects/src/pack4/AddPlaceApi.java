package pack4;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payLoads.RSAMapPayLoad;

import static org.hamcrest.Matchers.*;

import java.io.File;

import org.openqa.selenium.json.Json;

import static io.restassured.RestAssured.*;

public class AddPlaceApi {

	@Test
	public void addPlaceApi() {
		// Validate if Add Api is working as per expected

		// given:all input details
		// when:Submit to the Ap --resource (When Method)
		// then:Validate the Responses

		// Set BaseURI

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String resourcePath = "/maps/api/place/add/json";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(RSAMapPayLoad.addPlaceToGoogleMapsApi()).when().log().all().post(resourcePath).then().log().all()
				.assertThat().statusCode(200).body("scope", equalTo("APP")).body("status", equalTo("OK"))
				.header("Server", "Apache/2.4.18 (Ubuntu)");
	}
	
	@Test
	public void addPlaceStaticApi()throws Exception {
		// Validate if Add Api is working as per expected

		// given:all input details
		// when:Submit to the Ap --resource (When Method)
		// then:Validate the Responses

		// Set BaseURI

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String resourcePath = "/maps/api/place/add/json";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("./data/addPlaceApiRequest.json")))).when().log().all().post(resourcePath).then().log().all()
				.assertThat().statusCode(200).body("scope", equalTo("APP")).body("status", equalTo("OK"))
				.header("Server", "Apache/2.4.18 (Ubuntu)");
	}

	@Test
	public void addAndModifyPlaceInMaps() {
		String resourcePath = "/maps/api/place/add/json";
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type", "application/json").queryParam("key", "qaclick123")
				.body(RSAMapPayLoad.addPlaceToGoogleMapsApi()).when().log().all().post(resourcePath).then().log().all()
				.statusCode(200).body("scope", equalTo("APP")).body("status", equalTo("OK"))
				.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		System.out.println("Response" + response);
		JsonPath jsonPath = new JsonPath(response); // to Parse String response to JSON
		String placeID = jsonPath.get("place_id");
		System.out.println("Place ID" + placeID);

		// To update the Place in API
		String putPathParameters = "/maps/api/place/update/json";
		String modifiedAddress = "Samruddhi Nagar, North Carolina, USA";
		String modiFyPlaceResponse = given().log().all().header("Content-Type", "application/json")
				.queryParam("key", "qaclick123").queryParam("place_id", placeID)
				.body(RSAMapPayLoad.updatePlaceAPIPayload(placeID, modifiedAddress)).when().log().all()
				.put(putPathParameters).then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated")).extract().response().asString();
		System.out.println("ModifiedResponse" + modiFyPlaceResponse);

		String capturedResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
				.when().get("/maps/api/place/get/json").then().assertThat().statusCode(200).extract().response()
				.asString();
		System.out.println("Captured Get Response:" + capturedResponse);
		JsonPath jsonPathCapturedGet = new JsonPath(capturedResponse);
		String actualAddress = jsonPathCapturedGet.get("address");
		Assert.assertEquals(actualAddress, modifiedAddress);

		// To delete the Address Created
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(RSAMapPayLoad.deletePlaceApiPayload(placeID)).when().log().all().post("/maps/api/place/delete/json")
				.then().log().all().assertThat().statusCode(200).body("status", equalTo("OK")).header("Server", "Apache/2.4.18 (Ubuntu)");

	}

}
