package pack1;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;


//Login to JIRA to create session using login API
//Add a comment to an existing issue using add Comment API
//Add an attachment to an existing issue using add attachment api
//Get Issue details and check if attachment added is existing and comment is updated in the issue

//Curly braces means path parameters

public class Demo_Aa5 {
	
	@Test
	public void loginToJIRA() throws Exception {
		SessionFilter filter = new SessionFilter();
		RestAssured.baseURI="http://localhost:8085/";
		String response = given().relaxedHTTPSValidation().header("Content-Type", "application/json").body(payLoads.RSAMapPayLoad.loginJIRAPayload()).log().all().filter(filter).when()
		.post("rest/auth/1/session/").then().log().all().extract().response().asString();
		System.out.println(response);
		
		//Code to make a comment
		String addCommentPostRequest = given().header("Content-Type","application/json").pathParam("id", "10101").log().all().body(payLoads.RSAMapPayLoad.addcommentInIssue()).filter(filter).when()
		.post("rest/api/2/issue/{id}/comment").then().log().all().assertThat().statusCode(201).extract().response().asString();
		 System.out.println(addCommentPostRequest);
		 JsonPath jsp = new JsonPath(addCommentPostRequest);
		 String commentId = jsp.getString("id");
		 System.out.println(commentId);
		 //Add Attachment need Curl Command
		 given().header("X-Atlassian-Token","nocheck").filter(filter).pathParam("id","10101").header("Content-Type", "multipart/form-data").multiPart("file", new File("./data/JIRA.txt")).when().post("rest/api/2/issue/{id}/attachments")
		 .then().log().all().assertThat().statusCode(200);
		 
		 //Get Issue
		String getResponse= given().filter(filter).pathParam("id","10101").queryParam("fields", "comment").when().get("rest/api/2/issue/{id}").then().log().all().extract().response().asString();
		System.out.println(getResponse);
		JsonPath jsp1 = new JsonPath(getResponse);
		int commentArray = jsp1.getInt("fields.comment.comments.size()");
		System.out.println(commentArray);
		ArrayList<String>arr = new ArrayList<String>();
		for(int i=0;i<commentArray;i++) {
			String commentedIssue = (String)jsp1.get("fields.comment.comments["+i+"].id");		
			if(commentedIssue.equalsIgnoreCase(commentId)) {
				 String messageBody = (String)jsp1.get("fields.comment.comments["+i+"].body");
				 System.out.println(messageBody);
				 //Assert.assertEquals to match comments
			}
			
		}	 
	}

}
