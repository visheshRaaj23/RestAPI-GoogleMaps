package pack4;

import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import net.bytebuddy.description.ModifierReviewable.OfAbstraction;
import payLoads.RSAMapPayLoad;

import static io.restassured.RestAssured.given;

public class ComplexJsonParser {

	@Test
	public void _JSONParser() {
		// Mock Response

		JsonPath jsonParser = new JsonPath(RSAMapPayLoad.courseDetails());
		Map<String,Integer> courseDetails = new HashMap<String,Integer>();
		// Print the number of courses returned by API.
		int count = jsonParser.getInt("courses.size()");
		System.out.println("Number of courses returned by API:" + count);
		long totalPurchaseAmount = jsonParser.getInt("dashboard.purchaseAmount");
		System.out.println(totalPurchaseAmount);
		String firstCourseTitle = jsonParser.getString("courses[0].title");
		System.out.println(firstCourseTitle);
		int totalFee = 0;
		for (int i = 0; i < count; i++) {
			String courseTitle =  jsonParser.getString("courses["+i+"].title");
			int courseFee = jsonParser.getInt("courses[" + i + "].price");
			int number_Of_Copies = jsonParser.getInt("courses[" + i + "].copies");
			totalFee = totalFee +courseFee*number_Of_Copies;
			courseDetails.put(courseTitle, courseFee*number_Of_Copies);
		}
		System.out.println(totalFee);
		Assert.assertEquals(totalFee,totalPurchaseAmount);
		System.out.println(courseDetails);
		Integer rpaCoursePrice = courseDetails.get("RPA");
		System.out.println(rpaCoursePrice);
	}

}
