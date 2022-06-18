package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageObject {
	

	public static String header = "https://dev.novamcaas.com:8443/customer/#/login";
	
	
	@FindBy(xpath = "/html/body/app-root/mat-drawer-container/mat-drawer-content/app-login/div/div/div/div[2]/div/div/form/div[1]/input")
	public WebElement userName;
	
	@FindBy(xpath = "/html/body/app-root/mat-drawer-container/mat-drawer-content/app-login/div/div/div/div[2]/div/div/form/div[2]/input")
	public WebElement passWord;
	
	@FindBy(xpath = "/html/body/app-root/mat-drawer-container/mat-drawer-content/app-login/div/div/div/div[2]/div/div/form/div[3]/input")
	public WebElement signIn;
	
	@FindBy(xpath = "/html/body/div/div")
	public WebElement toastContainer;
	
	@FindBy(xpath = "/html/body/app-root/nav/div[2]/img[2]")
	public WebElement logOut;

}
