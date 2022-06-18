package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
	
	Properties prop;


	public ReadConfig() {


		File src = new File("./ConfigFiles/Config.properties");
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
	
	public String baseURI() {
		
		return prop.getProperty("baseUrl");
	}


}
