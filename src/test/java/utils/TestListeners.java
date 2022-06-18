package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import testCases.BaseClass;





public class TestListeners implements ITestListener   {
	
	
		private static ExtentReports extentreport = BaseClass.createInstance();
		public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

		public void onTestStart(ITestResult result) {
			
			ExtentTest testcase = extentreport.createTest(result.getMethod().getRealClass().getName().replace("testCases.", "")+" | | "+result.getMethod().getMethodName());
			extentTest.set(testcase);
		}
		
		public void onTestSuccess(ITestResult result) {
			
			
//			extentTest.get().log(Status.PASS,result.getMethod().getMethodName()+" Success");
		
		}
		
		public void onTestFailure(ITestResult result) {
			
			
//			extentTest.get().log(Status.FAIL,result.getMethod().getMethodName()+" Failed");
			

		}
			
		public void onTestSkipped(ITestResult result) {
			
			extentTest.get().log(Status.SKIP,result.getMethod().getMethodName()+ " Skipped");
			
		}
		
		public void onFinish(ITestContext context) {
			

			
			extentreport.flush();
		}


}
