package pkgwrite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pkgreadfiles.ReadFiles;
import pkgutil.ProductPage;


public class WriteFiles {
	
	public int rowid = 1;
	public int colid = 0;
	boolean flag;
	
	public void writeExcelData() throws IOException{
	String resultFilePath = ReadFiles.resultPath +"Result_"+ ReadFiles.logDate + ".xlsx";
	File file = new File(resultFilePath);
	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet spreadSheet = workbook.createSheet("MissingModelImage_Report");
	XSSFRow row;
	XSSFCellStyle cellStyle1, cellStyle;
	XSSFFont font1, font;
	ProductPage pp = new ProductPage();

	try{   
		
		//Write Header
		row = spreadSheet.createRow(0);
		spreadSheet.setColumnWidth(0, 6000);
		spreadSheet.setColumnWidth(1, 10000);
		spreadSheet.setColumnWidth(2, 4000);
		spreadSheet.setColumnWidth(3, 4000);
		spreadSheet.setColumnWidth(4, 4000);
		spreadSheet.setColumnWidth(5, 8000);
		spreadSheet.setColumnWidth(6, 25000);
		
		cellStyle1 = workbook.createCellStyle();
		font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 11);
		font1.setBold(true);
		cellStyle1.setBorderBottom(BorderStyle.MEDIUM);
		cellStyle1.setBorderLeft(BorderStyle.MEDIUM);
		cellStyle1.setBorderRight(BorderStyle.MEDIUM);
		cellStyle1.setBorderTop(BorderStyle.MEDIUM);
		cellStyle1.setAlignment(HorizontalAlignment.CENTER);
		cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle1.setDataFormat((short)BuiltinFormats.getBuiltinFormat("text"));
		cellStyle1.setWrapText(true);
		cellStyle1.setFont(font1);
		
		row.createCell(0).setCellValue("Product Code");
		row.getCell(0).setCellStyle(cellStyle1);
    	row.createCell(1).setCellValue("Product Description");
    	row.getCell(1).setCellStyle(cellStyle1);
    	row.createCell(2).setCellValue("Category");
    	row.getCell(2).setCellStyle(cellStyle1);
    	row.createCell(3).setCellValue("Missing Model");
    	row.getCell(3).setCellStyle(cellStyle1);
    	row.createCell(4).setCellValue("Total Number Of Images");
    	row.getCell(4).setCellStyle(cellStyle1);
    	row.createCell(5).setCellValue("Missing Images");
    	row.getCell(5).setCellStyle(cellStyle1);
    	row.createCell(6).setCellValue("Product URL");
    	row.getCell(6).setCellStyle(cellStyle1);
    	//row.createCell(6).setCellValue("All Product Images");	
    	
    	cellStyle = workbook.createCellStyle();
		font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
		cellStyle.setDataFormat((short)BuiltinFormats.getBuiltinFormat("text"));
		cellStyle.setWrapText(true);
		cellStyle.setFont(font);		

		
   	for(int i=0;i<ProductPage.ProductCode.size();i++){	
    		
    		String strURL =  ProductPage.ProductCode.get(i);
    		String[] tempURL = strURL.split("~");
    		
			row = spreadSheet.createRow(rowid++);
			
			System.out.println("ProductCode = "+ tempURL[0]);
			row.createCell(0).setCellValue(tempURL[0]);
			row.getCell(0).setCellStyle(cellStyle);
			
			System.out.println("ProductDescription = "+ ProductPage.ProductDescription.get(i));
			row.createCell(1).setCellValue(ProductPage.ProductDescription.get(i));
			row.getCell(1).setCellStyle(cellStyle);
			
			System.out.println("Category = "+ tempURL[2]);
			row.createCell(2).setCellValue(tempURL[2]);
			row.getCell(2).setCellStyle(cellStyle);
			
			flag = pp.isModelMissing(ProductPage.ProductImages.get(i));
			System.out.println("Flag = "+flag);
			row.createCell(3).setCellValue(flag);
			row.getCell(3).setCellStyle(cellStyle);
			
			System.out.println("ProductImagesCount ="+ProductPage.ProductImagesCount.get(i));
			row.createCell(4).setCellValue(ProductPage.ProductImagesCount.get(i));
			row.getCell(4).setCellStyle(cellStyle);
			
			//System.out.println("ProductImages ="+ProductPage.ProductImages.get(i));
			row.createCell(5).setCellValue(ProductPage.ProductImages.get(i));
			row.getCell(5).setCellStyle(cellStyle);
			
			System.out.println("Product URL ="+tempURL[1]);
			row.createCell(6).setCellValue(tempURL[1]);
			row.getCell(6).setCellStyle(cellStyle);
			
    	}
    
    	FileOutputStream out = new FileOutputStream(file);
    	workbook.write(out);
    	out.close();
	}
	catch(IOException ex){
		System.out.print(ex.getMessage());
	}
        	
	}
}