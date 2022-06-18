package apiTesting;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;





public class MCaaS /* extends BaseClass */{
	

	//String token;
//	RequestSpecification httpRequest;
	String responseBody;
	boolean isPresent;


	//DEMO
	BaseClass baseClass = BaseClass.getInstance();
	RequestSpecification httpRequest = baseClass.httpRequest;
	Response response;
	String token; /* = baseClass.token */
	

	@Test(priority = 0)
	public void signIn() {
		
		//DEMO
//		BaseClass baseClass = BaseClass.getInstance();
//		RequestSpecification httpRequest = baseClass.httpRequest;
//		Response response = baseClass.response;
//		String token = baseClass.token;
		

		Map<String, Object> loginCredentials = new HashMap<String, Object>();
		loginCredentials.put("username", "sudoadmin");
		loginCredentials.put("password", "Reset@123");
//		httpRequest = 
//				given();
		httpRequest.header("Content-Type" , "application/json");
		baseClass.response = httpRequest
				.body(loginCredentials)
			.when()	
				.post("/auth/signIn");
		
		System.out.println("sigin resp - " + baseClass.response.asPrettyString());
		baseClass.responseBodyCondition(loginCredentials.toString());
		JSONObject jsonResponse = new JSONObject(baseClass.response.asPrettyString());
		token =  jsonResponse.getString("token");
		
		System.out.println("token 1 - " + token);
		
		Assert.assertEquals(200, baseClass.response.getStatusCode());
	
		
	}



	


	@Test(priority = 1)
	public void showEnterprisesList(){


		httpRequest.header("Content-Type" , "application/json");
		httpRequest.header("Authorization" , token);
		
		System.out.println("token 1 - " + token);

		baseClass.response = httpRequest
				.when()
					.get("enterprises/showEnterprisesList")
				.then()
					.statusCode(200)
					.body("data.enterprises[0].name", equalTo("enterprises/LC00h3iuyt"))
					.extract().response();

		System.out.println(baseClass.response.asPrettyString());
		baseClass.responseBodyCondition();

	}

	@Test(priority = 2)
	public void showAllEnterprises() {

		
		httpRequest.header("Content-Type" , "application/json");
		httpRequest.header("Authorization" , token);
		
		System.out.println("token 2 - " + token);
		
					httpRequest
				.when()
					.get("/enterprises/showEnterprisesList")
				.then()
					.statusCode(200);
					baseClass.responseBodyCondition();

	}
	
	@Test(priority = 3)
	public void showEnterpriseDetails() {
		
		
		Map<String, Object> showEnterprisDetailsBody = new HashMap<String, Object>();
		showEnterprisDetailsBody.put("enterpriseName", "enterprises/LC04csaztt");
		System.out.println("showEnterprisDetailsBody 3 - "+ showEnterprisDetailsBody);
		
		System.out.println("token 3 - " + token);

		baseClass.response = httpRequest
				.header("Content-Type" , "application/json")
				.header("Authorization" , token)
				.body(showEnterprisDetailsBody)
			.when()
					.post("enterprises/showEnterpriseDetails")
			.then()
					.extract().response();
			
			responseBody = baseClass.response.asPrettyString();
			System.out.println(" response.asPrettyString() - " +  baseClass.response.asPrettyString());
			
			isPresent = responseBody.contains("endnodes");
			System.out.println(isPresent);
			baseClass.responseBodyCondition(showEnterprisDetailsBody.toString());
				
	}
	
	@Test(priority = 4)
	public void signupForNewEnterprise() {
		
		
		baseClass.response = httpRequest
//						.header("Content-Type" , "application/json")
//						.header("Authorization", token)
					.when()
						.get("enterprises/signUpUrl")
					.then()
						.extract().response();
		
		responseBody = baseClass.response.asPrettyString();
		isPresent = responseBody.contains("endnodes");
		System.out.println(baseClass.response.asPrettyString());
		baseClass.responseBodyCondition();
		
		
	}
	
	






	@Test(priority = 5)
	public void showAllDevices() {
		
		baseClass.response = httpRequest
//					.header("Authorization", token)
					.queryParam("enterpriseName ", "enterprises/LC04csaztt")
			.when()
				.get("/enterprises/showAllDevices")
			.then()
				.extract().response();
		
		
		baseClass.responseBodyCondition();
				
	}


}
