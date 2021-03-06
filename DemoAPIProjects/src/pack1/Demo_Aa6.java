package pack1;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payLoads.RSAMapPayLoad;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class Demo_Aa6 {

	@Test(dataProvider = "booksData")
	public void addBookApi(String aisle, String isbn) {
		RestAssured.baseURI = "http://216.10.245.166";
		System.out.println(aisle + "," + isbn);
		String response = given().log().all().header("Content-Type", "application/json")
				.body(RSAMapPayLoad.addBookApi(aisle, isbn)).when().log().all().post("/Library/Addbook.php").then()
				.log().all().assertThat().statusCode(200).body("Msg", equalTo("successfully added")).extract()
				.response().asString();
		JsonPath jsonPath = new JsonPath(response);
		String uniqueID = jsonPath.getString("ID");
		System.out.println(uniqueID);

		given().log().all().header("Content-Type", "application/json").body(RSAMapPayLoad.deleteBook(uniqueID)).when()
				.log().all().post("/Library/DeleteBook.php").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("book is successfully deleted"));
	}

	@DataProvider(name = "booksData")
	public Object[][] getData() {

		return new Object[][] { { "Vishesh", "1021" }, { "Supriya", "2091" }, { "Avinash", "9081" }, { "Harshitha", "9082" },
				{ "Rakshitha", "9083" }, { "Vijay", "9084" } };

	}

}
