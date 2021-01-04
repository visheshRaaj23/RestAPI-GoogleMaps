package stepDefinations;

import io.cucumber.java.Before;
import resources.APIResources;

public class Hooks {

	// To Run the Pre-Conditions

	@Before("@DeletePlace")
	public void beforeDeletePlace() {
		// To Run only when Place Id is Null.
		StepDefination stepsDefObj = new StepDefination();
		if (StepDefination.placeID == null) {
			stepsDefObj.add_Place_Payload_with("Vishesh Nilaya", "German", "Karnataka, India");
			stepsDefObj.user_Calls_with_Post_http_request("AddPlaceAPI", "POST");
			stepsDefObj.verify_placeId_created_maps_to_using("Vishesh Nilaya", "GetPlaceAPI");
		}
	}

}
