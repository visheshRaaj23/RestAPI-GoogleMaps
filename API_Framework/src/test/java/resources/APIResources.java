package resources;

//Enum is the special kind of Class in Java, which has a collection of constants and methods.
public enum APIResources {

	AddPlaceAPI("/maps/api/place/add/json"), GetPlaceAPI("/maps/api/place/get/json"), ModifyPlaceAPI(
			"/maps/api/place/update/json"), DeletePlaceAPI("/maps/api/place/delete/json");
	private String resParam;

	APIResources(String resourceParameter) {
		this.resParam = resourceParameter;
	}

	public String getResourceParameter() {
		return resParam;
	}

}
