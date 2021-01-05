package pack4;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import payLoads.JIRA_Payloads;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.io.File;

import org.openqa.selenium.json.Json;

public class JIRA_Backend_Testing {

	@Test
	public void accompolishTasksInJIRA() {
		// For Login to JIRA
		RestAssured.baseURI = "http://localhost:8080";
		String uniqueComment = "Hi Vishesh, This issue is resolved now.Kindly, retest and revert if found when re-testing.";
		String adminUserName = "visheshraaj";
		String adminPassword = "DKhy23$#";
		SessionFilter sessionFilter = new SessionFilter();
		String authenticationResponse = given().relaxedHTTPSValidation().log().all()
				.header("Content-Type", "application/json")
				.body(JIRA_Payloads.returnAuthenticationPayload(adminUserName, adminPassword)).filter(sessionFilter)
				.when().log().all().post("/rest/auth/1/session").then().log().all().assertThat().statusCode(200)
				.extract().response().asString();
		System.out.println("Response" + authenticationResponse);
		JsonPath jsonPath = new JsonPath(authenticationResponse);
		String jsession = jsonPath.getString("session.name");
		String sessionValue = jsonPath.getString("session.value");
		String authKey = jsession.concat("=").concat(sessionValue);
		System.out.println("Jsession Authentication Key -->" + authKey);

		// To Add the issue
		String addIssueResponseJson = given().relaxedHTTPSValidation().log().all()
				.header("Content-Type", "application/json").header("Cookie", authKey)
				.body(JIRA_Payloads.createIssuePayload()).filter(sessionFilter).when().log().all()
				.post("/rest/api/2/issue/").then().log().all().assertThat().statusCode(201).extract().response()
				.asString();
		JsonPath jsonExtractor = new JsonPath(addIssueResponseJson);
		String getIssueID = (String) jsonExtractor.get("id");

		// To Add a comment
		String commentResponse = given().relaxedHTTPSValidation().log().all().pathParam("id", getIssueID)
				.header("Content-Type", "application/json").header("Cookie", authKey)
				.body(JIRA_Payloads.addCommentToIssue(uniqueComment)).filter(sessionFilter).when().log().all()
				.post("/rest/api/2/issue/{id}/comment").then().log().all().assertThat().statusCode(201).extract()
				.response().asString();
		JsonPath jsonPathComment = new JsonPath(commentResponse);
		long commentKey = jsonPathComment.getLong("id");
		System.out.println("Comment Key:-" + commentKey);

		// To Post the Attachment
		// For form data like attachments use multipart before when method, also pass
		// content-type multipart/form in headers.
		given().relaxedHTTPSValidation().pathParam("id", getIssueID).log().all().header("X-Atlassian-Token", "no-check")
				.header("Content-Type", "multipart/form-data").filter(sessionFilter)
				.multiPart("file", new File("./data/PDFContent_October2020.pdf")).when().log().all()
				.post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);

		String responseGet = given().relaxedHTTPSValidation().log().all().pathParam("id", getIssueID)
				.queryParam("fields", "comment").filter(sessionFilter).when().log().all().get("/rest/api/2/issue/{id}")
				.then().log().all().statusCode(200).extract().response().asString();
		System.out.println("Response from Get Method" + responseGet);
		JsonPath jsonPathGetMethod = new JsonPath(responseGet);
		int totalCount = jsonPathGetMethod.getInt("fields.comment.comments.size()");
		System.out.println("Count of Records filtered:-->" + totalCount);
		for (int i = 0; i < totalCount; i++) {
			long uniqueID = jsonPathGetMethod.getLong("fields.comment.comments[" + i + "].id");
			if (uniqueID == commentKey) {
				String commentBody = jsonPathGetMethod.getString("fields.comment.comments[" + i + "].body");
				System.out.println(commentBody);
				Assert.assertEquals(uniqueComment, commentBody);
				break;
			} else {
				continue;
			}
		}

	}

}
