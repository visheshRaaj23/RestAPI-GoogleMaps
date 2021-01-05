package pack1;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import pojo_classes.AddBook_POJO;
import static org.hamcrest.Matchers.*;

public class Demo_Aa7 {

	@Test
	public void testAddBookApi() {
		RestAssured.baseURI = "http://216.10.245.166";
		AddBook_POJO addBookObj = new AddBook_POJO();
		SessionFilter filter = new SessionFilter();
		String addBookResponse = given().relaxedHTTPSValidation().log().all().header("Content-Type", "application/json")
				.body("").filter(filter).when().log().all().post("/Library/Addbook.php").then().log().all().assertThat()
				.statusCode(200).body("Msg", equalTo("successfully added")).and().contentType(ContentType.JSON)
				.extract().response().toString();

	}

}
