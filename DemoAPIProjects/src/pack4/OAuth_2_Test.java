package pack4;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import pojo_classes.GetCourse;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OAuth_2_Test {

	@Test
	public void testOAuthService() throws Exception {
		String getCodeUrl = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyjdss";
		String currentURL = "https://rahulshettyacademy.com/getCourse.php?state=verifyjdss&code=4%2F0AY0e-g56jHfwKXTTUtWu-MW2ZebfqXjKNxnVMan_TM-pJKHh7bLtsFrcGqEpP75da7q6Sw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none#";
		String splittedUrl = currentURL.split("code=")[1];
		String code = splittedUrl.split("&scope")[0];
		System.out.println(code);
		String accessTokenResponse = given().relaxedHTTPSValidation().urlEncodingEnabled(false).log().all()
				.queryParams("code", code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath jsonPath = new JsonPath(accessTokenResponse);
		String accessToken = jsonPath.getString("access_token");
		System.out.println(accessToken);

		// If response header doesn't have content-Type=application/Json then use
		// expect().defaultParser(defaultparser.JSON) to scan the response and treat it
		// as JSON.
		GetCourse getCourseObj = given().log().all().queryParam("access_token", accessToken).expect()
				.defaultParser(defaultParser.JSON).when().get("https://rahulshettyacademy.com/getCourse.php")
				.as(GetCourse.class);
		System.out.println(getCourseObj.getLinkedIn());
		System.out.println(getCourseObj.getExpertise());
		String courseSelect = "SoapUI";
		String[] expectedListOfCourses = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		ArrayList<String> listOfCourses = new ArrayList<String>();
		for (int i = 0; i < getCourseObj.getCourses().getApi().size(); i++) {
			String courseTitle = getCourseObj.getCourses().getApi().get(i).getCourseTitle();
			if (courseTitle.contains(courseSelect)) {
				String coursePrice = getCourseObj.getCourses().getApi().get(i).getPrice();
				System.out.println(coursePrice);
				break;
			} else {
				continue;
			}
		}
		for (int j = 0; j < getCourseObj.getCourses().getWebAutomation().size(); j++) {
			listOfCourses.add(getCourseObj.getCourses().getWebAutomation().get(j).getCourseTitle());
		}
		boolean result=Arrays.asList(expectedListOfCourses).containsAll(listOfCourses);
		System.out.println(result);
		Assert.assertTrue(result);

	}

}
