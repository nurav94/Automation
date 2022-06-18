package apiTesting;





import static io.restassured.RestAssured.given;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.ReadConfig;
import utils.TestListeners;

public class BaseClass {

	//DEMO
	public RequestSpecification httpRequest;
	public String token;


	Response response;


	ReadConfig readConfig = new ReadConfig();


	static ExtentSparkReporter spark;
	static ExtentTest testcase;
	public static ExtentReports extentreport;
	public static Logger logger;
	
	
	public BaseClass() {
		System.out.println("BaseClass Constructor");
		
		RestAssured.baseURI = readConfig.baseURI();
		logger = Logger.getLogger("Rest Assured AMAPI");
		PropertyConfigurator.configure("log4j.properties");
		
		httpRequest = given().header("Content-Type" , "application/json");
		
		
		
	}
	
	
	private static BaseClass singleBaseClass;
	public static BaseClass getInstance() {
		if(singleBaseClass != null) return singleBaseClass;
		
		singleBaseClass = new BaseClass();
		
		return singleBaseClass;
	}
	

	@BeforeSuite
	public void baseURI() {
		
		System.out.println("Before suit");

//		RestAssured.baseURI = readConfig.baseURI();
//		logger = Logger.getLogger("Rest Assured AMAPI");
//		PropertyConfigurator.configure("log4j.properties");
	}


	public static ExtentReports createInstance(){

		extentreport=new ExtentReports();
		spark = new ExtentSparkReporter(".//Myproject.html");
		spark.config().setTheme(Theme.STANDARD);
		spark.config().setDocumentTitle("MCaaS");
		extentreport.attachReporter(spark);
		extentreport.setSystemInfo("Project", "AMAPI - RestAssured");
		extentreport.setSystemInfo("Operating System", "Windows 10");
		extentreport.setSystemInfo("Browser and Version", "Chrome 98");
		extentreport.setSystemInfo("Tester", "Varun");
		return extentreport;
	}
	
	private void printResponseBody(String body) {
//		if(response.asPrettyString().contains("\"success\": true"))  {
		if(this.response.asPrettyString().contains("\"success\": true"))  {
			System.out.println("inside true");
			TestListeners.extentTest.get().pass("API Success");
			
			System.out.println("this.response.statusCode() - " + this.response.statusCode());
			
			TestListeners.extentTest.get().pass("The Status Code is: "+ this.response.statusCode());
			TestListeners.extentTest.get().info("The Time taken for the API Call: "+ this.response.getTime()+" milliseconds");
			
			if(body != null)
				TestListeners.extentTest.get().info("Request Body: "+ body);

		}else{
			System.out.println("inside false");
			TestListeners.extentTest.get().fail("Response Body: "+ this.response.body().asPrettyString());
			TestListeners.extentTest.get().fail("The Status Code is: "+ this.response.statusCode());
			TestListeners.extentTest.get().info("The Time taken for the API Call: "+ this.response.getTime()+" milliseconds");
		}
	}

	public void responseBodyCondition(String body) {
		printResponseBody(body);
	}
	public void responseBodyCondition(){
		printResponseBody(null);
	}
	
	
	
}
