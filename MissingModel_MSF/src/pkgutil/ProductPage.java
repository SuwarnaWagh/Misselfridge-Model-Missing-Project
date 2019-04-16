package pkgutil;

import java.util.ArrayList;


import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;




import pkgreadfiles.ReadFiles;
import pkgwebdriverfactory.LocalDriverManager;

public class ProductPage {
	
	public static List<String> ProductCode=new ArrayList<String>();
	public static List<String> ProductDescription=new ArrayList<String>();
	public static List<String> ProductImages=new ArrayList<String>();
	public static List<String> AllImages=new ArrayList<String>();
	public static List<Integer> ProductImagesCount=new ArrayList<Integer>();
	
	public void getProductData(String url) {
		WebElement prod_code;
		List<WebElement> modelImagesLists = null;
		String productCode,imagesDesc,desc,imageList;
		
		try{
			
			String[] tempURL = url.split("~");
			ReadFiles.Category.add(tempURL[1]);
			ReadFiles.Category = (List<String>) ReadFiles.Category.stream().distinct().collect(Collectors.toList());
			LocalDriverManager.getDriver().get(tempURL[0]);
			Thread.sleep(1000);			
			//Product Code
			boolean isProductCodeAvailable = isElementPresent(By.xpath((ReadFiles.xpathToGetProductCode)));
			if (isProductCodeAvailable){
				prod_code = LocalDriverManager.getDriver().findElement(By.xpath(ReadFiles.xpathToGetProductCode));
			    productCode = prod_code.getText();
			    if(productCode == null || productCode.isEmpty()) { 
			    	productCode = "Not Available";
			    }
			}
			else{
				productCode = "Not Available";
			}		
			System.out.println("Product Code - " + productCode + "  " + tempURL[0]);
			
			//Product Description
			desc = LocalDriverManager.getDriver().getTitle();
			int k = desc.indexOf(" - ", desc.indexOf(" - "));
			imagesDesc = desc.substring(0,k);			
			ProductDescription.add(imagesDesc);
			System.out.println("Product Description - " + imagesDesc);
			
			
			//Image List
			modelImagesLists = LocalDriverManager.getDriver().findElements(By.xpath(ReadFiles.xPathToGetModelImagesData));
			ProductImagesCount.add(modelImagesLists.size());
			System.out.println("Total Image Count - " + modelImagesLists.size());
			
			ProductCode.add(productCode + "~" + tempURL[0] + "~" + tempURL[1]);
			
			imageList = getmodelImagesLists(modelImagesLists,productCode);			
			ProductImages.add(imageList);
			
			System.out.println("Missing Image List - " + imageList);
			
		}catch(NoSuchElementException ex){
			System.out.println("Error in getProductData:- " + ex.getMessage());
		}
		catch(Exception ex){
			System.out.println("Error in getProductData:- " + ex.getMessage());
		}
		
	}
	
	public String getmodelImagesLists(List<WebElement> lst,String productcode ){
		String images="",BmissingData=null,M1missingData=null,M2missingData=null,DmissingData=null;
		String img_missingData="";
		String SEPARATOR = "~";
		if((productcode != "Not Available")){
			StringBuilder imgBuilder = new StringBuilder();
			for(WebElement ele : lst){
			  imgBuilder.append(ele.getAttribute("href"));
			  imgBuilder.append(SEPARATOR);
			}
		  String imageRef = imgBuilder.toString();
		  AllImages.add(imageRef);
		  imageRef = imageRef.substring(0, imageRef.length() - SEPARATOR.length());			
			//System.out.println("List Of Images =="+imageRef);
			if(!(imageRef.contains("_B_1"))){
				BmissingData = "MS"+productcode+"_B_1.jpg";    			
			}
			if(!(imageRef.contains("_M_1"))){    
				M1missingData = "MS"+productcode+"_M_1.jpg";
			}
			if(!(imageRef.contains("_M_2"))){ 
				M2missingData = "MS"+productcode+"_M_2.jpg";
			}
		
			if(!(imageRef.contains("_D_1"))){
				DmissingData = "MS"+productcode+"_D_1.jpg";
			}
			
			images = (BmissingData + "  " + M1missingData + "  " + M2missingData + "  " + DmissingData);
			img_missingData = images.replaceAll("[null]", "");
		}
		
		return img_missingData;
	}
	
	public boolean isModelMissing(String modelImg){
		//System.out.println(modelImg);
		if((modelImg.contains("_B_1") && modelImg.contains("_M_1") && modelImg.contains("_D_1") && modelImg.contains("_M_2"))){//&& modelImg.contains("_2_P_1")
			return true;
		}else{
			return false;
		}

	}
	
	public boolean isElementPresent(By by) {
	    try {
	    	LocalDriverManager.getDriver().findElement(by);
	        return true;
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	}

	
}
