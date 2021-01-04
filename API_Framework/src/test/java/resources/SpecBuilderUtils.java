package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import io.cucumber.gherkin.internal.com.eclipsesource.json.Json;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilderUtils {

	public static RequestSpecification requestBuild; // To share the requestSpecification throughout the Execution

	public RequestSpecification requestSpecification() throws Exception {

		if (requestBuild == null) {
			String baseUrl = this.getPropertyValue("baseUrl");
			// For Request,SET
			// For Response, Expect
			RestAssured.baseURI = baseUrl;
			PrintStream loggingFile = new PrintStream(new FileOutputStream(this.getPropertyValue("outputFile")));
			requestBuild = new RequestSpecBuilder().setBaseUri(baseUrl).addQueryParam("key", "qaclick123")
					.setContentType(ContentType.JSON).addFilter(RequestLoggingFilter.logRequestTo(loggingFile))
					.addFilter(ResponseLoggingFilter.logResponseTo(loggingFile)).build();
		}
		return requestBuild;
	}

	public ResponseSpecification responseSpecificationAddPlace() {
		ResponseSpecification resposeSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		return resposeSpec;
	}

	public String getPropertyValue(String parameter) throws Exception {
		Properties property = new Properties();
		FileInputStream fileInputStream = new FileInputStream("./src/test/java/resources/application.properties");
		property.load(fileInputStream);
		String parameterValue = (String) property.get(parameter);
		System.out.println(parameterValue);
		return parameterValue;
	}

	public static String returnJsonPath(String response, String key) {
		String value = null;
		try {
			JsonPath jsonPath = new JsonPath(response);
			value = jsonPath.getString(key);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return value;
	}

}
