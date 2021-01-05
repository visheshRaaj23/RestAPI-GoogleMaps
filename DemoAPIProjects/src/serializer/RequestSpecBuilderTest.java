package serializer;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo_classes.Add_Place_POJO;
import pojo_classes.Location_AddPlace_POJO;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

public class RequestSpecBuilderTest {

	@Test
	public void SerializerTest() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		// For Request,SET
		// For Response, Expect
		RequestSpecification requestBuild = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		ResponseSpecification resposeSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		Add_Place_POJO pojoMapsApiObj = new Add_Place_POJO();
		List<String> typeList = new ArrayList<String>();
		typeList.add("shoe park");
		typeList.add("shop");
		pojoMapsApiObj.setAccuracy(23);
		pojoMapsApiObj.setName("Samruddhi Enclave");
		pojoMapsApiObj.setPhone_number("(+91) 974 251 7177");
		pojoMapsApiObj.setAddress("No 9, Dattatreya Extension,KG Nagar,Gavipuram Guttahalli, Bengaluru-19");
		pojoMapsApiObj.setWebsite("https://www.linkedin.com/in/vishesh-raaj-a26883157");
		pojoMapsApiObj.setLanguage("English-UK");
		pojoMapsApiObj.setTypes(typeList);
		Location_AddPlace_POJO locObj = new Location_AddPlace_POJO();
		locObj.setLat(-59.383494);
		locObj.setLng(33.427362);
		pojoMapsApiObj.setLocation(locObj);

		String responseString = given().relaxedHTTPSValidation().spec(requestBuild).body(pojoMapsApiObj).log().all().when()
				.post("/maps/api/place/add/json").then().spec(resposeSpec).extract().response().asString();
		System.out.println(responseString);

	}

}
