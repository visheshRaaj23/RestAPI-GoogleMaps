package pack1;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Demo_Aa4 {
	
	//Port 8085
	
	@Test
	public void getDataFromJsonFile() throws Exception {
		RestAssured.baseURI="http://216.10.245.166/";
		String response = given().header("Content-Type","application/json").body(payLoads.RSAMapPayLoad.generateStringFromResourceFile("./data/BooksResource.json")).when().post("Library/Addbook.php").then().statusCode(200).extract().response().asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String authId=js.get("ID");
		System.out.println(authId);
	}

}
