package pack1;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Demo_Aa3 {
	
	//Advanced Payload Creation Strategies
	//1.Dynamically build json payload with external data inputs.
	//Parametrize the API tests with multiple data sets.
	//Sending static payload into the post method directly of rest assured
	//Feed JSON payload from using excel using hashmap
	//Library API
	
	@Test(dataProvider="BooksData")
	public void addBookToApi(String bookName,String isbn,String aisle,String authorName) throws Exception {
		
		RestAssured.baseURI="http://216.10.245.166/";
		String response = given().header("Content-Type","application/json").body(payLoads.RSAMapPayLoad.addBookApi(bookName,isbn,aisle,authorName)).when().post("Library/Addbook.php").then().statusCode(200).extract().response().asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String authId=js.get("ID");
		System.out.println(authId);
	}
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		
		//Collection of Arrays -->Multidimensional Arrays
		return new Object[][] {
			{"Think and Grow Rich","VishUduu","232324","VisheshRaaj M"},
			{"Sex and The City","VishUduupp","232325","VisheshRaaj M Udupa"},
			{"Padmaavat","RanveerSingha","232324","visheshBhattt"},
			{"BajiRaoMastani","RanveerSingh","232425","Ranveer singh"},
			{"Ramleela","RanveerSingh24","232624","Deepika Padukne"}
		};
	}

}
