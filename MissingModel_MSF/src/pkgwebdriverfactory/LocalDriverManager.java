package pkgwebdriverfactory;

import org.openqa.selenium.WebDriver;

//Class : LocalDriverManager
//Created 12/02/2018
//---------------------------------------------------------------------------------------------------------------------------------------------------
//####  DO NOT CHANGE WITHOUT AUTHORISATION       ####
//####  PLEASE MAINTAIN CHANGE LOG                ####
//---------------------------------------------------------------------------------------------------------------------------------------------------
//Amended By     : None
//On            : None
//Reason        : None
//Amended By     : None
//On            : None
//Reason        : None
//--------------------------------------------------------------------------------------------------------------------------------------------------
//CONTENTS
//LocalDriverManager [Parameters - ]
//Description :- This method creates local thread for each method instances and return local thread driver  

public class LocalDriverManager {
	
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	 
    public static WebDriver getDriver() {
    	return webDriver.get();
    }
 
    static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }
	

}
