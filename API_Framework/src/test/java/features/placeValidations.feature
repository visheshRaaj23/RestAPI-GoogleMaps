Feature: Validating Place API's 

@AddPlace @Regresssion
Scenario Outline: Verify if the place is successfully being added to AddPlaceAPI 

	Given Add Place Payload with "<name>" "<language>" "<address>" 
	
	When User Calls "AddPlaceAPI" with "Post" http request 
	
	Then The API call should be successful with the StatusCode "200" 
	
	And "status" in the Response body is "OK" 
	
	And "scope" in the Response body is "APP" 
	
	And verify placeId created maps to "<name>" using "GetPlaceAPI" 
	
	#To Comment the lines
	
	Examples: 
		|name              |language|address                           |
		|Kadambari Nilaya  |Kannada | Hanumantha Nagara, Bengaluru-19  |
		|Hanumagiri Nilaya |Panjabi |SanjayLal Nagar, Haryana|
		|Samruddhi Nilaya  |Tulu    |Gavipuram Guttahalli, Bengaluru-19|
	
@DeletePlace @Regresssion
Scenario: Verify If Delete place Functionality is working 

	Given DeletePlace Payload 
	
	When User hits "DeletePlaceAPI" with "Post" http request
	
	Then The API call should be successful with the StatusCode "200" 
	
	And "status" in the Response body is "OK"
	
	
