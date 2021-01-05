package pack1;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payLoads.RSAMapPayLoad;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;
import java.lang.String;




public class Demo_Aa1 {
	
	@Test
	public void runAPI() throws Exception {
		// TODO Auto-generated method stub
		
		//Validate Add place is working as expected
		
		//Given-->All Input details
		//When -->Submit the details to API--->Resource and HTTP methods
		//Then -->Validate the Response
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(RSAMapPayLoad.addPlaceAPI()).when().post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");
		System.out.println("Place Id: "+placeId);
		
		//For updating the address
		
		String newAddress = "Sangameshwar Road RailwayStation , Sindhu Durg,Konkan Region of Maharastra India";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(RSAMapPayLoad.updateAddressApi(placeId,newAddress)).when().put("/maps/api/place/update/json").then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).extract().asString();
		
		
		//For Getting all details from place_id
		String getResponse=given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("/maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath json1 = new JsonPath(getResponse);
		String apiAddress = json1.getString("address");
		
		if(apiAddress.equalsIgnoreCase(newAddress)) {
			System.out.println("Test is Pass");
		}else {
			System.out.println("Test is Failed");
		}
		//Cucumber Testng Junit Framework

	}

}
