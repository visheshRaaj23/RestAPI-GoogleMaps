package payLoads;

public class JIRA_Payloads {

	public static String returnAuthenticationPayload(String userName, String password) {
		String payload = "{ \"username\": \"" + userName + "\", \"password\": \"" + password + "\" }";
		return payload;
	}

	public static String addCommentToIssue(String uniqueComment) {
		String payLoad = "{\r\n" + "    \"body\": \"" + uniqueComment + "\",\r\n" + "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n" + "        \"value\": \"Administrators\"\r\n" + "    }\r\n" + "}";
		return payLoad;
	}

	public static String createIssuePayload() {
		String payload = "{\r\n" + "    \"fields\": {\r\n" + "       \"project\":\r\n" + "       {\r\n"
				+ "          \"key\": \"NIMBLY\"\r\n" + "       },\r\n"
				+ "       \"summary\": \" 10% Discount is not applying for the return journeys though the passenger count is 4\",\r\n"
				+ "       \"description\": \"The journey type return selected with 4 passengers, The discount of 10% coupen should be applied by default.But, if the internet disconnection happens to the device, The discount option is disappeared from the UI\",\r\n"
				+ "       \"issuetype\": {\r\n" + "          \"name\": \"Bug\"\r\n" + "       }\r\n" + "   }\r\n" + "}";
		return payload;
	}

}
