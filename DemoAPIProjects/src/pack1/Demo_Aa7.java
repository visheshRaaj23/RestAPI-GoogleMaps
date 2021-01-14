package pack1;

import org.testng.annotations.Test;

import dataDriven.ExcelDataDriver;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import payLoads.RSAMapPayLoad;
import pojo_classes.AddBook_POJO;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

public class Demo_Aa7 extends ExcelDataDriver {

	@Test
	public void testAddBookApi() {
		RestAssured.baseURI = "http://216.10.245.166";
		Map<Integer, List<String>> mapData = this.getData();
		for (int map = 0; map < mapData.size(); map++) {
			AddBook_POJO addBookObj = new AddBook_POJO();
			List<String> bookRowContents = mapData.get(map);
			addBookObj.setName(bookRowContents.get(0));
			addBookObj.setIsbn(bookRowContents.get(1));
			addBookObj.setAisle(bookRowContents.get(2));
			addBookObj.setAuthor(bookRowContents.get(3));
			String response = given().log().all().header("Content-Type", "application/json")
					.body(addBookObj).when().log().all().post("/Library/Addbook.php").then()
					.log().all().assertThat().statusCode(200).body("Msg", equalTo("successfully added")).extract()
					.response().asString();
			JsonPath jsonPath = new JsonPath(response);
			String uniqueID = jsonPath.getString("ID");
			System.out.println(uniqueID);

			given().log().all().header("Content-Type", "application/json").body(RSAMapPayLoad.deleteBook(uniqueID)).when()
					.log().all().post("/Library/DeleteBook.php").then().log().all().assertThat()
					.contentType(ContentType.JSON).statusCode(200).body("msg", equalTo("book is successfully deleted"));
		}

	}

}
