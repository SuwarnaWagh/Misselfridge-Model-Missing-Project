package pkgtestmethods;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pkgEmailReport.EmailReport;
import pkgmainclass.SplitArray;
import pkgreadfiles.ReadFiles;
import pkgutil.ProductPage;
import pkgwrite.WriteFiles;

@Listeners(pkgwebdriverfactory.WebDriverListener.class)
public class TestCase1 {
	ReadFiles rf = new ReadFiles();
	public static List<String> list;
	public static long sTime; 
	
	@BeforeTest
	public void TestReadProperties() {
		sTime=System.currentTimeMillis();
		rf.readPropertyFile();
	}
	
	@Test(priority=1)
	public void TestReadNavigationMatrix() throws InterruptedException {
		//list = rf.getProductURL();
		list = rf.readCategoryUrl();
		//rf.readLeftHandNavigationMatrix();
	}
	
	@Test(dataProvider = "data",priority=2)
	public void Test1(List<String> listURLs) throws IOException, InterruptedException{
		
		System.out.println(listURLs.size());
		for(int i=0;i<listURLs.size();i++){
			String strURL =  listURLs.get(i);
    		String[] tempURL = strURL.split("~");
    		rf.getProductURL(tempURL[1],tempURL[0]);
		}
	}
	
	@Test(dataProvider = "data1",priority=3)
	public void Test2(List<String> listURLs) throws IOException, InterruptedException{
		
		ProductPage dn = new ProductPage();
		System.out.println(listURLs.size());
		for(int i=0;i<listURLs.size();i++){
			String strURL = listURLs.get(i);
			dn.getProductData(strURL);
		}
	}
	
	@Test(priority=4)
	public void testWriteData() throws IOException
	{	
		WriteFiles wf = new WriteFiles();
		wf.writeExcelData();
		EmailReport er = new EmailReport();
		er.sendReport(ReadFiles.mailTO,ReadFiles.mailCC,ReadFiles.resultPath + "\\Result_"+ReadFiles.logDate+".xlsx", "Result_"+ReadFiles.logDate+".xlsx");
		
	}
	
	@DataProvider(name="data", parallel=true)
	public Iterator<Object[]> data() {
		List<List<String>> mySplitString = new ArrayList<List<String>>();

		mySplitString = SplitArray.splitArray(list, ReadFiles.ThreadCount2);
		Collection<Object[]> dp = new ArrayList<Object[]>();
		
		for(List<String> pageURL : mySplitString){
			dp.add(new Object[] {pageURL});
		}
		
		return dp.iterator();
		
	}
	
	@DataProvider(name="data1", parallel=true)
	public Iterator<Object[]> data1() {
		List<List<String>> mySplitString = new ArrayList<List<String>>();

		mySplitString = SplitArray.splitArray(ReadFiles.ProductlistURL, ReadFiles.ThreadCount1);
		Collection<Object[]> dp = new ArrayList<Object[]>();
		
		for(List<String> pageURL : mySplitString){
			dp.add(new Object[] {pageURL});
		}
		
		return dp.iterator();
		
	}
	

}
