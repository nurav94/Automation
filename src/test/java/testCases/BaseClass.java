package testCases;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.sun.org.apache.bcel.internal.classfile.Method;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ReadConfig;
import utils.TestListeners;

public class BaseClass   {


	ReadConfig readConfig = new ReadConfig();

	public static WebDriver driver;
	static ExtentSparkReporter spark;
	static ExtentTest testcase;
	public static ExtentReports extentreport;
	public static Logger logger;


	public static ExtentReports createInstance(){

		extentreport=new ExtentReports();
		spark = new ExtentSparkReporter(".//Myproject.html");
		spark.config().setTheme(Theme.STANDARD);
		spark.config().setDocumentTitle("MCaaS");
		extentreport.attachReporter(spark);
		extentreport.setSystemInfo("Project", "FrameworkCreation");
		extentreport.setSystemInfo("Operating System", "Windows 10");
		extentreport.setSystemInfo("Browser and Version", "Chrome 98");
		extentreport.setSystemInfo("Tester", "Varun");
		return extentreport;
	}

	public static void getScreenshots() {

		String timestamp = new SimpleDateFormat("MM_dd__hh_mm_ss").format(new Date());
		TakesScreenshot sourcefile = ((TakesScreenshot)driver);
		File srcFile =sourcefile.getScreenshotAs(OutputType.FILE);	
		File destFile=new File(".//Screenshots//"+timestamp+".png");
		try {
			FileHandler.copy(srcFile,destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		TestListeners.extentTest.get().addScreenCaptureFromPath(".//Screenshots//"+timestamp+".png");

	}

	public static WebDriver startBrowser(String browser, String url) throws Exception {

		if(driver != null) return driver;

		if (browser.equalsIgnoreCase("Chrome")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--disable-notifications");
			//			chromeOptions.addArguments("--incognito");
			//			chromeOptions.addArguments("--headless");
			//chromeOptions.addArguments("window-size=1366,625");

			chromeOptions.setAcceptInsecureCerts(true);
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(chromeOptions);
			driver.manage().window().maximize();

		} else if (browser.equalsIgnoreCase("Edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();

		}

		else if (browser.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else {

			throw new Exception("Incorrect Browser");
		}


		driver.get(url);


		return driver;

	}

	public boolean isAlertPresent() {


		try {
			driver.switchTo().alert();
			System.out.println("alertPresent");
			return true;
			
		}
		catch(NoAlertPresentException e) {
			System.out.println("alertNotPresent");
			return false;
		}
	}


	@BeforeSuite
	public void openBrowser() throws Exception {

		startBrowser("chrome",readConfig.getAppUrl());
		logger = Logger.getLogger("Android Management API");
		PropertyConfigurator.configure("Log4j.properties");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@AfterSuite
	public void closeBrowser() {

		driver.close();
		extentreport.flush();
	}


}
