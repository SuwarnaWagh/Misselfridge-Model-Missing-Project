package pkgreadfiles;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import pkgwebdriverfactory.LocalDriverManager;

public class ReadFiles {
	
	public static Logger log;
	public static FileHandler fileTxt;
	public static long sTime;
	public static String logDate,browser,resultPath,ChromeDriverPath,baseurl,xpathToGetProductURL,xpathToGetProductCode,xPathToGetModelImagesData,enviornment,site,mailTO,mailCC,categoryToRun;
	public static int ThreadCount1,ThreadCount2;
	
	public static HashMap<String,String> Basehmap = new HashMap<String,String>();
	public static List<String> ProductlistURL=new ArrayList<String>();
	public static List<String> Basemap=new ArrayList<String>();
	public static List<String> Category=new ArrayList<String>();
	
	public final static String strPropertyFilePath="./Configuration/configuration.properties";

	public ReadFiles(){
		
	}
	
	public void readPropertyFile(){
		Properties pf= new Properties();
		try{
				
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd___HH_mm");
			sTime=System.currentTimeMillis();
			logDate = dateFormat.format(sTime).toString();
			
			pf.load(new FileReader(strPropertyFilePath));
			
			mailTO=pf.getProperty("mailTO");
			mailCC=pf.getProperty("mailCC");
			ThreadCount1 = Integer.parseInt(pf.getProperty("ThreadCountForAllProductURL"));
			ThreadCount2 = Integer.parseInt(pf.getProperty("ThreadCountForCategoryURL"));
			browser = pf.getProperty("browserName");
			resultPath = pf.getProperty("resultPath");
			ChromeDriverPath=pf.getProperty("ChromeDriverPath");
			baseurl=pf.getProperty("baseurl");
			xpathToGetProductURL=pf.getProperty("xPathToGetProductURL");
			xpathToGetProductCode=pf.getProperty("xPathToGetProductCode");
			xPathToGetModelImagesData = pf.getProperty("xPathToGetModelImagesData");
			enviornment = pf.getProperty("env");
			site=pf.getProperty("site");
			categoryToRun= pf.getProperty("categoryToRun");
		
		}
		catch(IOException ex){
			System.out.println("Error in readPrpertyFile - " + " " + ex.getMessage());
		}
	}
	
	public void getProductURL(String CategoryURL, String Category) {
		
		WebElement resultCount;
		List<WebElement> activelinks;
		//List<String> strings = new ArrayList<String>();
		try{
		LocalDriverManager.getDriver().get(CategoryURL);
				
		  /*activelinks = LocalDriverManager.getDriver().findElements(By.xpath(xpathToGetProductURL));
	   	    System.out.println("\n\n Links are --->" +activelinks.size());
	   	    for(int j=0;j<activelinks.size();j++){	   	    		
	   	    		System.out.println(""+activelinks.get(j).getAttribute("href"));	   	    	
	   	    		    strings.add(activelinks.get(j).getAttribute("href"));	   	    		
	   	    	} */
	/********************************************************************For ScrollDown**************************************************************/
		resultCount = LocalDriverManager.getDriver().findElement(By.xpath("//div[@class='control_block total_count']/span"));
	    //System.out.println(resultCount.getText());
	    
	    int rsCnt = Integer.parseInt(resultCount.getText());
	    JavascriptExecutor js =(JavascriptExecutor)LocalDriverManager.getDriver();
	    //WebElement scroll = LocalDriverManager.getDriver().findElement(By.id("cmd_catalognavigationsearchresultcmd"));
	    
	  // while(!(strings.size() >= rsCnt)){
			   boolean reachedbottom = Boolean.parseBoolean(js.executeScript("return $(document).height() == ($(window).height() + $(window).scrollTop());").toString());
               while (!reachedbottom) {                                         
			    activelinks = LocalDriverManager.getDriver().findElements(By.xpath(xpathToGetProductURL));
			    System.out.println("\n\n Links are --->" +activelinks.size());	   
			    		for(WebElement e : activelinks){
			    			//System.out.println(e.getAttribute("href"));
			    			ProductlistURL.add(e.getAttribute("href") + "~" + Category);
			    			ProductlistURL = (List<String>) ProductlistURL.stream().distinct().collect(Collectors.toList());
			    		    
			    		}				    		
			    		js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
			    		Thread.sleep(2000);
                        reachedbottom=Boolean.parseBoolean(js.executeScript("return $(document).height() == ($(window).height() + $(window).scrollTop());").toString());
                        System.out.println(reachedbottom);
                        
			    		if(ProductlistURL.size()>=35){
			    			break;
			    		}
               }
              // }
	   		System.out.println("List contain = "+ProductlistURL.size() + "\t\tResultCount  on UI = "+rsCnt);
		 	
		}
		catch(Exception ex){
			System.out.println("Error in getProductURL : - "+ ex.getMessage());
		}
	}
	
	//To get the country name and Url from the input file
		public List<String> readCategoryUrl(){
			String strCategory,strCategoryURL,blntoRunFlag;
			List<String> lstCategoryURL=new ArrayList<String>();
		try{
			Workbook workbookobj = null;
			//READ COUNTRY NAME 

			File fp= new File(categoryToRun + "MissingModelImage.xlsx");		
			try {
				workbookobj= new XSSFWorkbook(fp);			
			} catch (Exception e) {		
				e.printStackTrace();
			}			
			XSSFSheet newCatSheet= (XSSFSheet) workbookobj.getSheet("CategoryList");	
			int rowcount=1;		
			int lastRowNumer=newCatSheet.getLastRowNum();
			while(rowcount<=lastRowNumer){
				blntoRunFlag = newCatSheet.getRow(rowcount).getCell(3).getStringCellValue();
				if(!(blntoRunFlag.contains("FALSE"))){
					strCategory= newCatSheet.getRow(rowcount).getCell(1).getStringCellValue();				
					strCategoryURL= newCatSheet.getRow(rowcount).getCell(2).getStringCellValue();
					lstCategoryURL.add(strCategory + "~" + strCategoryURL);
				}
				rowcount++;
			}
			return lstCategoryURL;
		}
		catch(Exception ex){
			System.out.println("Error in readCategoryUrl : - "+ ex.getMessage());
			return null;
		}
	}
	
}
