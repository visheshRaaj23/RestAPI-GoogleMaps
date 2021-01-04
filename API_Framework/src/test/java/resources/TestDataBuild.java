package resources;

import java.util.ArrayList;
import java.util.List;

import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo_classes.Add_Place_POJO;
import pojo_classes.Location_AddPlace_POJO;

public class TestDataBuild {

	public Add_Place_POJO addPlacePayload(String name, String langauge, String address) throws Exception {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		// For Request,SET
		// For Response, Expect
		Add_Place_POJO pojoMapsApiObj = new Add_Place_POJO();
		List<String> typeList = new ArrayList<String>();
		typeList.add("shoe park");
		typeList.add("shop");
		pojoMapsApiObj.setAccuracy(23);
		pojoMapsApiObj.setName(name);
		pojoMapsApiObj.setPhone_number("(+91) 974 251 7177");
		pojoMapsApiObj.setAddress(address);
		pojoMapsApiObj.setWebsite("https://www.linkedin.com/in/vishesh-raaj-a26883157");
		pojoMapsApiObj.setLanguage(langauge);
		pojoMapsApiObj.setTypes(typeList);
		Location_AddPlace_POJO locObj = new Location_AddPlace_POJO();
		locObj.setLat(-59.383494);
		locObj.setLng(33.427362);
		pojoMapsApiObj.setLocation(locObj);
		return pojoMapsApiObj;
	}

	public String deletePlaceApi(String placeID) {
		String payload = "{\r\n" + "    \"place_id\": \"" + placeID + "\"\r\n" + "}";
		return payload;
	}

}
