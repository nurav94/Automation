package testCases;

import java.io.IOException;
import java.time.Duration;
import java.util.Base64;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.LoginPageObject;
import utils.ExcelUtils;


public class LoginPage extends BaseClass {

	

	
	String actualUrl;
	String currentUrl;
	LoginPageObject loginObjects;
	
	
	
	

	@Test(priority = 0)
	public void checkUrl() {

//		TestListeners.extentTest.get().info("Checking the URL");
		
		logger.info("Checking URL");
		if(driver.getCurrentUrl().equals(LoginPageObject.header)) {
//			TestListeners.extentTest.get().pass("The URL is Matched");
			logger.info("The URL is Matched");
		}

		else {
//			TestListeners.extentTest.get().fail("The URL is Not Matched");
			System.out.println(BaseClass.driver.getCurrentUrl());
			logger.info("The URL is not Matched");
		}
	}
	
	
	@Test(priority = 1)
	public void invalidLogin() throws InterruptedException {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		loginObjects = PageFactory.initElements(BaseClass.driver, LoginPageObject.class);
		loginObjects.userName.sendKeys("endnodes");
		loginObjects.passWord.sendKeys("Reset@12");
		loginObjects.signIn.click();
		
		actualUrl = "https://dev.novamcaas.com:8443/customer/#/login";
		currentUrl = BaseClass.driver.getCurrentUrl();
		
		if(actualUrl.equals(currentUrl)) {
			
//			TestListeners.extentTest.get().pass("User is not allowed to login with invalid credentials");
			getScreenshots();
		}
		else {
			
//			TestListeners.extentTest.get().fail("User is allowed to login with invalid credentials");
			getScreenshots();
		}
	}
	
	@Test(priority = 3 )
	public void validLogin() throws InterruptedException {
		
		BaseClass.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		loginObjects = PageFactory.initElements(BaseClass.driver, LoginPageObject.class);
		loginObjects.userName.clear();
		loginObjects.passWord.clear();
		loginObjects.userName.sendKeys(readConfig.getUsername());
		loginObjects.passWord.sendKeys(readConfig.getPassword());
		System.out.println("password  "+readConfig.getPassword());
		loginObjects.signIn.click();
		
		actualUrl = "https://dev.novamcaas.com:8443/customer/#/dashboard";
		currentUrl = BaseClass.driver.getCurrentUrl();
		
		if(actualUrl.equals(currentUrl)) {
			
//			TestListeners.extentTest.get().fail("User is not allowed to login with invalid credentials");
			getScreenshots();
		}
		else {
			
//			TestListeners.extentTest.get().pass("User is allowed to login with invalid credentials");
			getScreenshots();
		}
		
		
	}
	
	
	@Test(priority = 4)
	public void logOut() throws InterruptedException {
		
		loginObjects = PageFactory.initElements(BaseClass.driver, LoginPageObject.class);
		Thread.sleep(5000);
		loginObjects.logOut.click();
		Thread.sleep(5000);
		isAlertPresent();
		passwordEncryption();
		
		
	}
	@Test(dataProvider = "LoginData" )
	public void dataDrivenTesting(String user, String pwd) {
		
		loginObjects = PageFactory.initElements(BaseClass.driver, LoginPageObject.class);
		loginObjects.userName.sendKeys(user);
		loginObjects.passWord.sendKeys(pwd);
		loginObjects.signIn.click();
		getScreenshots();
	
	}
	
	public void passwordEncryption() {
		
		String encrptData= "Reset@123";
		
		byte[] encodedBytes = Base64.getEncoder().encode(encrptData.getBytes());
				
		System.out.println("encodedBytes --------------->" + new String(encodedBytes));

	}
	
	@DataProvider(name="LoginData")
	String [][] getData() throws IOException{
	String path = readConfig.getExcel()+"testData.xlsx";
	
	int rownum=ExcelUtils.getRowCount(path,"Sheet1");
	int colcount=ExcelUtils.getCellCount(path,"Sheet1",1);
	String logindata[][] = new String[rownum][colcount];
	for(int i=1;i<=rownum;i++) {
		
		for(int j=0;j<colcount;j++) {
			
			logindata[i-1][j] = ExcelUtils.getCellData(path,"Sheet1",i,j);
			
		}
	}
	return logindata;
	}
	
}	

