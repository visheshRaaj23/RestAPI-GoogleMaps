package payLoads;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RSAMapPayLoad {

	public static String addPlaceAPI() {

		return "{\r\n" + "\r\n" + "    \"location\":{\r\n" + "\r\n" + "        \"lat\" : -39.765429,\r\n" + "\r\n"
				+ "        \"lng\" : 37.427365\r\n" + "\r\n" + "    },\r\n" + "\r\n" + "    \"accuracy\":45,\r\n"
				+ "\r\n" + "    \"name\":\"Tipsy Bull RestoBar\",\r\n" + "\r\n"
				+ "    \"phone_number\":\"(+91) 974 251 7177\",\r\n" + "\r\n"
				+ "    \"address\" : \"Koramangala,Bangalore Karnataka,India\",\r\n" + "\r\n"
				+ "    \"types\": [\"Veg/Non Veg,RestoBar\",\"Hotel\"],\r\n" + "\r\n"
				+ "    \"website\" : \"http://google.com\",\r\n" + "\r\n" + "    \"language\" : \"French-IN\"\r\n"
				+ "\r\n" + "}\r\n" + "\r\n" + "";
	}

	public static String updateAddressApi(String place_Id, String addressNew) {

		String payload = "{\r\n" + "\"place_id\":\"" + place_Id + "\",\r\n" + "\"address\":\"" + addressNew + "\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n" + "}\r\n" + "";

		return payload;

	}

	public static String courseDetails() {

		String payload = "{\r\n" + "\r\n" + "\"dashboard\": {\r\n" + "\r\n" + "\"purchaseAmount\": 910,\r\n" + "\r\n"
				+ "\"website\": \"rahulshettyacademy.com\"\r\n" + "\r\n" + "},\r\n" + "\r\n" + "\"courses\": [\r\n"
				+ "\r\n" + "{\r\n" + "\r\n" + "\"title\": \"Selenium Python\",\r\n" + "\r\n" + "\"price\": 50,\r\n"
				+ "\r\n" + "\"copies\": 6\r\n" + "\r\n" + "},\r\n" + "\r\n" + "{\r\n" + "\r\n"
				+ "\"title\": \"Cypress\",\r\n" + "\r\n" + "\"price\": 40,\r\n" + "\r\n" + "\"copies\": 4\r\n" + "\r\n"
				+ "},\r\n" + "\r\n" + "{\r\n" + "\r\n" + "\"title\": \"RPA\",\r\n" + "\r\n" + "\"price\": 45,\r\n"
				+ "\r\n" + "\"copies\": 10\r\n" + "\r\n" + "}\r\n" + "\r\n" + "]\r\n" + "\r\n" + "}\r\n" + "\r\n" + "";
		return payload;
	}

	public static String addBookApi(String bookName, String isbn, String aisle, String authorName) {
		String payload = "{\r\n" + "\r\n" + "\"name\":\"" + bookName + "\",\r\n" + "\"isbn\":\"" + isbn + "\",\r\n"
				+ "\"aisle\":\"" + aisle + "\",\r\n" + "\"author\":\"" + authorName + "\"\r\n" + "}\r\n" + "";

		return payload;
	}

	public static String generateStringFromResourceFile(String filePath) throws Exception {
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}

	public static String loginJIRAPayload() {

		String payload = "{ \"username\": \"visheshraaj\", \"password\": \"DKhy23$#\" }";

		return payload;

	}

	public static String addcommentInIssue() {
		String payload = "{\r\n"
				+ "	\"body\":\"Hi This is my first Comment Supriya Shankar, I really really in love with you\",\r\n"
				+ "	\"visibility\":{\r\n" + "		\"type\":\"role\",\r\n" + "		\"value\":\"Administrators\"\r\n"
				+ "	}\r\n" + "}";

		return payload;
	}

	public static String addPlaceToGoogleMapsApi() {
		String payLoad = "{\r\n" + "  \"location\": {\r\n" + "    \"lat\": -41.383494,\r\n"
				+ "    \"lng\": 32.427362\r\n" + "  },\r\n" + "  \"accuracy\": 50,\r\n"
				+ "  \"name\": \"Manjunatha Nilaya\",\r\n" + "  \"phone_number\": \"(+91) 814 733 9389\",\r\n"
				+ "  \"address\": \"Gavipuram Guttahalli,KG Nagar, Bangalore-19,India\",\r\n" + "  \"types\": [\r\n"
				+ "    \"shoe park\",\r\n" + "    \"shop\"\r\n" + "  ],\r\n"
				+ "  \"website\": \"http://google.com\",\r\n" + "  \"language\": \"French-IN\"\r\n" + "}";

		return payLoad;
	}

	public static String updatePlaceAPIPayload(String placeID, String address) {
		String payLoad = "{\r\n" + "\"place_id\":\"" + placeID + "\",\r\n" + "\"address\":\"" + address + "\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n" + "}";
		return payLoad;
	}

	public static String deletePlaceApiPayload(String placeID) {
		String payLoad = "{\r\n" + "    \"place_id\":\"" + placeID + "\"\r\n" + "}";
		return payLoad;
	}
	
	public static String addBookApi(String aisle,String isbn) {
		String payLoad  = "{\r\n" + 
				"\"name\":\"Learn Appium Automation with Java\",\r\n" + 
				"\"isbn\":\""+isbn+"\",\r\n" + 
				"\"aisle\":\""+aisle+"\",\r\n" + 
				"\"author\":\"John foe\"\r\n" + 
				"}";
		return payLoad;
	}
	
	public static String deleteBook(String uniqueID) {
		String payLoad = "{\r\n" + 
				" \r\n" + 
				"\"ID\" : \""+uniqueID+"\"\r\n" + 
				" \r\n" + 
				"} \r\n" + 
				"";
		return payLoad;
	}

}
