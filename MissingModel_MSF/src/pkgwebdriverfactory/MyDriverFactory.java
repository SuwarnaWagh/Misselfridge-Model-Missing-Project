package pkgwebdriverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import pkgreadfiles.ReadFiles;

//Class : LocalDriverFactory
//Created 12/02/2018
//---------------------------------------------------------------------------------------------------------------------------------------------------
//####  DO NOT CHANGE WITHOUT AUTHORISATION       ####
//####  PLEASE MAINTAIN CHANGE LOG                ####
//---------------------------------------------------------------------------------------------------------------------------------------------------
//Amended By            : None
// On            : None
// Reason        : None
//Amended By           : None
// On            : None
// Reason        : None
//--------------------------------------------------------------------------------------------------------------------------------------------------
//CONTENTS
//createInstance [Parameters - browserName]
//Description :- This method returns driver as per browser name 

class LocalDriverFactory {
	
	static WebDriver createInstance(String browserName) {
        WebDriver driver = null;
        if (browserName.toLowerCase().contains("firefox")) {
        	System.setProperty("webdriver.gecko.driver", "C:\\Users\\P10251420\\workspace\\International_Brands_Total&Category_ItemCount\\drivers\\geckodriver.exe");
            driver = new FirefoxDriver();
            //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            //driver.get(MainClass.baseUrl);
            return driver;
        }
        if (browserName.toLowerCase().contains("internet")) {
        	System.setProperty("webdriver.internetexplorer.driver", "C:\\Users\\P10251420\\workspace\\International_Brands_Total&Category_ItemCount\\drivers\\IEDriverServer");
            driver = new InternetExplorerDriver();
            return driver;
        }
        if (browserName.toLowerCase().contains("chrome")) {
        	
        	System.setProperty("webdriver.chrome.driver", ReadFiles.ChromeDriverPath);
        	ChromeOptions options = new ChromeOptions();
        	options.addArguments("test-type");
        	//options.addArguments("headless");
            //options.addArguments("window-size=1200x600");
        	options.addArguments("start-maximized");
        	options.addArguments("--no-sandbox");
        	options.addArguments("--disable-extensions");
        	//options.setBinary("C:\\Program Files (x86)\\Chrome 54\\chrome32_54.0.2840.71\\chrome.exe");
        	options.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");//C:\Program Files (x86)\Google\Chrome\Application
        						
            driver = new ChromeDriver(options);
            return driver;
        }
        return driver;
    }

}
