package pkgwebdriverfactory;

import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import pkgreadfiles.ReadFiles;

//Class : WebDriverListener
//Created 09/01/2018
//---------------------------------------------------------------------------------------------------------------------------------------------------
//####  DO NOT CHANGE WITHOUT AUTHORISATION       ####
//####  PLEASE MAINTAIN CHANGE LOG                ####
//---------------------------------------------------------------------------------------------------------------------------------------------------
//Amended By            : None
//On            : None
//Reason        : None
//Amended By           : None
//On            : None
//Reason        : None
//--------------------------------------------------------------------------------------------------------------------------------------------------
//CONTENTS
//beforeInvocation [Parameters - ]
//afterInvocation  [Parameters - ]
//Description :- This is method invoker implemented for webdriver. This call right before and after the method.


public class WebDriverListener implements IInvokedMethodListener{
	
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		if(method.isTestMethod()){
			WebDriver driver = LocalDriverFactory.createInstance(ReadFiles.browser); 
			LocalDriverManager.setWebDriver(driver);
		}
	}
	
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if(method.isTestMethod()){
			WebDriver driver = LocalDriverManager.getDriver();
			if(driver != null){
				driver.quit();
			}
		}
		
	}

}
