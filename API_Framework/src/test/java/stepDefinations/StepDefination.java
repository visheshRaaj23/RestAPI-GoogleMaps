package stepDefinations;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo_classes.Add_Place_POJO;
import pojo_classes.Location_AddPlace_POJO;
import resources.APIResources;
import resources.SpecBuilderUtils;
import resources.TestDataBuild;

public class StepDefination extends SpecBuilderUtils {
	public static RequestSpecification reqRes;
	public static ResponseSpecification resposeSpec;
	public static Response response;
	public static String capturedResponse;
	public static String placeID;

	TestDataBuild testDataBuildObj = new TestDataBuild();

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_Place_Payload_with(String name, String language, String address) {
		try {
			reqRes = given().log().all().relaxedHTTPSValidation().spec(requestSpecification())
					.body(testDataBuildObj.addPlacePayload(name, language, address));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@When("User Calls {string} with {string} http request")
	public void user_Calls_with_Post_http_request(String resourceParameters, String httpRequestType) {
		try {
			resposeSpec = responseSpecificationAddPlace();
			String resourceParamString = APIResources.valueOf(resourceParameters).getResourceParameter();
			if (httpRequestType.equalsIgnoreCase("POST")) {
				response = reqRes.log().all().when().log().all().post(resourceParamString).then().log().all()
						.spec(resposeSpec).extract().response();
				System.out.println("Response String"+response.asString());
			} else if (httpRequestType.equalsIgnoreCase("GET")) {
				response = reqRes.log().all().when().log().all().get(resourceParamString).then().log().all()
						.spec(resposeSpec).extract().response();
				System.out.println(response);
			} else if (httpRequestType.equalsIgnoreCase("PUT")) {
				response = reqRes.log().all().when().log().all().put(resourceParamString).then().log().all()
						.spec(resposeSpec).extract().response();
				System.out.println(response);
			} else if (httpRequestType.equalsIgnoreCase("DELETE")) {
				response = reqRes.log().all().when().log().all().delete(resourceParamString).then().log().all()
						.spec(resposeSpec).extract().response();
				System.out.println(response);
			}
			capturedResponse = response.asString();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Then("The API call should be successful with the StatusCode {string}")
	public void the_API_call_should_be_successfull_with_the_StatusCode(String statusCode) {
		try {
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Then("{string} in the Response body is {string}")
	public void in_the_Response_body_is(String string, String string2) {
		try {
			
			Assert.assertEquals(SpecBuilderUtils.returnJsonPath(capturedResponse, string), string2);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Then("verify placeId created maps to {string} using {string}")
	public void verify_placeId_created_maps_to_using(String name, String httpApiRequestType) {
		try {
			System.out.println(capturedResponse);
			placeID = SpecBuilderUtils.returnJsonPath(capturedResponse, "place_id");
			System.out.println("PlaceID:" + placeID);
			String getResponse = given().spec(requestSpecification()).queryParam("place_id", placeID).log().all().when()
					.log().all().get(APIResources.valueOf(httpApiRequestType).getResourceParameter()).then()
					.spec(resposeSpec).extract().response().asString();
			String actualName = SpecBuilderUtils.returnJsonPath(getResponse, "name");
			Assert.assertEquals(actualName, name);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Given("DeletePlace Payload")
	public void deleteplace_payload() throws Exception {
		reqRes = given().spec(requestSpecification()).body(testDataBuildObj.deletePlaceApi(placeID));
	}

	@When("User hits {string} with {string} http request")
	public void user_calls_something_with_something_http_request(String strArg1, String strArg2) throws Exception {
		String deleteResponse = reqRes.when().log().all().post(APIResources.valueOf(strArg1).getResourceParameter())
				.then().spec(resposeSpec).extract().response().asString();
		System.out.println(deleteResponse);
	}

}
