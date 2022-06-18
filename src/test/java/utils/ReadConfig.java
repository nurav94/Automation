package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.Properties;

public class ReadConfig {


	Properties prop;


	public ReadConfig() {


		File src = new File("./ConfigFiles/config.properties");
		try {
			FileInputStream fis = new FileInputStream(src);
			prop = new Properties();
			prop.load(fis);
		}
		catch (Exception e) {
			System.out.println("Exception is " + e.getMessage());
			e.printStackTrace();
		}
	}


	public String getAppUrl() {

		String url = prop.getProperty("baseUrl");
		return url;

	}

	public String getUsername() {

		String userName = prop.getProperty("userName");
		return userName;
	}
	
	
	

	public String getPassword() {

		String password = prop.getProperty("password");
		byte[] decodeBytes = Base64.getDecoder().decode(password.getBytes());
		System.out.println("decodedBytes --------------->" + new String(decodeBytes));
		password = new String(decodeBytes);
		return password;
	}

	public String getExcel() {

		String excelPath = prop.getProperty("excelPath");
		return excelPath;
	}
}
