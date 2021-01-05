package serializer;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import pojo_classes.Add_Place_POJO;
import pojo_classes.Location_AddPlace_POJO;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

public class SerializationTest {

	@Test
	public void SerializerTest() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
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

		String responseString = given().relaxedHTTPSValidation().log().all().body(pojoMapsApiObj).log().all().queryParam("key", "qaclick123").when()
				.post("/maps/api/place/add/json").then().statusCode(200).extract().response().asString();
		System.out.println(responseString);

	}

}
