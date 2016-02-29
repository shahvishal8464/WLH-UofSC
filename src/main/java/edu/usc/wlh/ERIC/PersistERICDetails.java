package edu.usc.wlh.ERIC;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class PersistERICDetails {
	
	private static String abstractFilePath = "abstracts/ERIC/";
	private static int column_count=0;
	private static int common_row_count=0;
	private static int yearly_row_count=0;
	
	private File file = null; 
	private static XSSFWorkbook common_workbook= null;
	private static XSSFWorkbook yearly_workbook= null;
	private static XSSFSheet common_sheet=null;
	private static XSSFSheet yearly_sheet=null;
	private Row row=null; 	
	
	public void createPath(String term){
		file = new File(abstractFilePath+term);		
		if(!file.exists())
			try {
				if(file.mkdirs()){
					System.out.println("Dirctory Created");
					System.out.println(file.getAbsolutePath());
				}
				else{
					System.out.println("Unable to Create Folder :"+file.getAbsolutePath());
				}
			} catch (Exception e) {
				System.out.println("Unable to create Folder:");
				System.out.println(file.getAbsolutePath());
			}
	}
	
	
	public void persistToWordDocument(ERICObject object){
		XWPFDocument document= new XWPFDocument();
		FileOutputStream out;
		try {
			out = new FileOutputStream( new File(abstractFilePath+"/docs/"+object.getDate_Published().replaceAll("\\D+","")+"/ED"+object.getERIC_ID().replaceAll("\\D+","")+".docx"));
			XWPFParagraph paragraph = document.createParagraph();
			
			XWPFRun run_1=paragraph.createRun();
			run_1.setFontSize(12);
			run_1.setText("Journal : "+object.getJournal() + " : "+ object.getDate_Published());
			run_1.addBreak();
			
			XWPFRun run_2=paragraph.createRun();
			run_2.setBold(true);
			run_2.setFontSize(16);
			run_2.setText(object.getTitle());
			run_2.addBreak();
			
			XWPFRun run_3=paragraph.createRun();
			run_3.setFontSize(12);
			run_3.setText("Authors : "+object.getAuthors().toString());
			run_3.addBreak();
			
			XWPFRun run_4=paragraph.createRun();
			run_4.setFontSize(12);
			run_4.setText("ERIC ID: "+object.getERIC_ID());
			run_4.addBreak();
			
			XWPFRun run_5=paragraph.createRun();
			run_5.setFontSize(12);
			run_5.setText("Keywords : "+object.getKeywords().toString());
			run_5.addBreak();
			run_5.addBreak();
			
			XWPFRun run_6=paragraph.createRun();
			run_6.setFontSize(12);
			run_6.setText("Abstract : \n"+object.getAbstract());
			run_6.addBreak();
			
			document.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void persistToTextFile(ERICObject object){
		File outputFile;
		BufferedWriter writer;
		try {
			outputFile = new File(abstractFilePath+"/text/"+object.getDate_Published().replaceAll("\\D+","")+"/ED"+object.getERIC_ID().replaceAll("\\D+","")+".txt");
			if (!outputFile.exists()){
				writer = new BufferedWriter(new FileWriter(outputFile));
				writer.write("ERIC_ID : "+object.getERIC_ID()+"\n");
				writer.write("Title : "+ object.getTitle()+"\n");
				writer.write("Authors : "+ object.getAuthors()+"\n");
				writer.write("Journal : "+ object.getJournal()+"\n");
				writer.write("Publication Date : "+ object.getDate_Published()+"\n");
				writer.write("Keywords : "+object.getKeywords()+"\n");
				writer.write("Abstract Text : \n"+ object.getAbstract());
				writer.close();	
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public void createCommonExcelSheet(){
		common_workbook = new XSSFWorkbook();
		common_sheet = common_workbook.createSheet("Default");
		common_row_count=0;
	}

	public void addRowCommonExcelSheet(ERICObject object){
		row = common_sheet.createRow(common_row_count++);
		column_count = 0;
		
		Cell cell = row.createCell(column_count ++);
		cell.setCellValue(object.getERIC_ID());
		
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getDate_Published());
		
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getTitle());
		
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getJournal());
		
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getAuthors().toString());
		
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getKeywords().toString());

	}
	
	public void persistCommonExcelSheet(){
		try {
			FileOutputStream outputStream = new FileOutputStream(abstractFilePath+"/ERIC_Publications.xlsx"); 
            common_workbook.write(outputStream);
            System.out.println("Written "+ common_row_count+" Records To File : ERIC_Publications.xlsx");
		}catch (Exception e) {
			System.out.println("Could Not Write To File : ERIC_Publications.xlsx");
			e.printStackTrace();
		}
	}
	
	public void createYearlyExcelSheet(){
		yearly_workbook = new XSSFWorkbook();
		yearly_sheet = yearly_workbook.createSheet("Default");
		yearly_row_count=0;           		
	}

	public void addRowYearlyExcelSheet(ERICObject object){
		row = yearly_sheet.createRow(yearly_row_count++);
		column_count = 0;
		
		Cell cell = row.createCell(column_count ++);
		cell.setCellValue(object.getERIC_ID());
		
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getDate_Published());
		
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getTitle());
		
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getJournal());
		
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getAuthors().toString());
		
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getKeywords().toString());
		
	}

	public void persistYearlyExcelSheet(String year){
		try {
			FileOutputStream outputStream = new FileOutputStream(abstractFilePath+"/Publications_"+year+".xlsx"); 
            yearly_workbook.write(outputStream);
            System.out.println("Written "+yearly_row_count+" RecordsTo File : Publications_"+year+".xlsx");
		}catch (Exception e) {
			System.out.println("Could Not Write To File : Publications_"+year+".xlsx");
			e.printStackTrace();
		}
	}
}