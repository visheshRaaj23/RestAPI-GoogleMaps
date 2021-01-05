package pack1;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.TestOnly;
import org.testng.annotations.Test;



import io.restassured.path.json.JsonPath;

public class Demo_Aa2 {
	
	@TestOnly
	public void parseJSONInput() throws Exception {
		JsonPath js = new JsonPath(payLoads.RSAMapPayLoad.courseDetails());
		int countOfArray = js.getInt("courses.size()");
		System.out.println(countOfArray);
		long purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		Map<String,String> courseInfo = new HashMap<String,String>();
		for(int i=0;i<countOfArray;i++) {
			
			courseInfo.put((String)js.getString("courses["+i+"].title"),Integer.toString(js.getInt("courses["+i+"].price")));
			
		}
		System.out.println(courseInfo);
		
		for(int j=0;j<courseInfo.size();j++) {
			
			if(courseInfo.containsKey("RPA")) {
				System.out.println("Number of copies sold for RPA: "+js.getString("courses["+j+"].copies"));
				break;
			}
			
		}
		long totalprice=0;
		for(int k=0;k<courseInfo.size();k++) {
			long copies = Long.parseLong(js.getString("courses["+k+"].copies"));
			long prices = js.getInt("courses["+k+"].price");
			totalprice = totalprice + copies *prices;
			
		}
		System.out.println(totalprice);
		if(totalprice == purchaseAmount) {
			System.out.println("Test is Pass");
		}else {
			System.out.println("Test is failed");
		}
		
		
	}

}
