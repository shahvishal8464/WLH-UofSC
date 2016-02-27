package edu.usc.core.pubmed;

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

public class PersistResults {

	private static String abstractFilePath = "abstracts/";
	private int column_count=0;
	
	public File createPath(String term, File file){
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
		return file;
	}
				
	public void persistToWordDocument(PubMedObject pmo, String relative_path){
		XWPFDocument document= new XWPFDocument();
		FileOutputStream out;
		try {
			out = new FileOutputStream( new File(abstractFilePath+relative_path+pmo.getPubMed_Id()+".docx"));
			XWPFParagraph paragraph = document.createParagraph();
			
			XWPFRun run_1=paragraph.createRun();
			run_1.setBold(true);
			run_1.setFontSize(16);
			run_1.setText(pmo.getTitle());
			run_1.addBreak();
			
			XWPFRun run_2=paragraph.createRun();
			run_2.setFontSize(12);
			run_2.setText(pmo.getJournal_Name()+" Publication Date : "+pmo.getPublished_month()+"/"+pmo.getPublished_year());
			run_2.addBreak();
			
			XWPFRun run_3=paragraph.createRun();
			run_3.setFontSize(12);
			run_3.setText(pmo.getAuthors().toString());
			run_3.addBreak();
			
			XWPFRun run_4=paragraph.createRun();
			run_4.setFontSize(12);
			run_4.setText(pmo.getGrants().toString());
			run_4.addBreak();
			
			XWPFRun run_5=paragraph.createRun();
			run_5.setFontSize(12);
			run_5.setText(pmo.getAbstract_Text());
			run_5.addBreak();
			
			XWPFRun run_6=paragraph.createRun();
			run_6.setFontSize(12);
			run_6.setText(pmo.getAbstract_Text());
			run_6.addBreak();
			
			document.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void persistToWordDocument(String abstract_text, File file, String PubMedID){
		XWPFDocument document= new XWPFDocument();
		FileOutputStream out;
		try {
			out = new FileOutputStream( new File(file.getAbsolutePath()+"/"+PubMedID+".docx"));
			XWPFParagraph paragraph = document.createParagraph();			
			XWPFRun run_1=paragraph.createRun();
			run_1.setFontSize(12);
			run_1.setText(abstract_text);
			document.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void persistToTextFile(String abstract_text, File file, String PubMedID){
		File outputFile;
		BufferedWriter writer;
		try {
			outputFile = new File(file.getAbsolutePath()+"/"+ PubMedID + ".txt");
			if (!outputFile.exists()){
				writer = new BufferedWriter(new FileWriter(outputFile));
				writer.write(abstract_text);
				writer.close();	
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
		
	
	public XSSFSheet createExcelSheet(XSSFWorkbook workbook, String SheetName){
		XSSFSheet sheet = workbook.createSheet(SheetName);
		return sheet;
	}	
	
	public XSSFWorkbook createExcelWorkBook(){
		XSSFWorkbook workbook = new XSSFWorkbook();
		return workbook;
	}	
	
	public void addRow(XSSFSheet sheet, PubMedObject pmo, int row_count){
		Row row = sheet.createRow(row_count);
		column_count = -1;
		
		Cell cell = row.createCell(++column_count);
		cell.setCellValue(pmo.getPubMed_Id());
		
		cell = row.createCell(++column_count);
		cell.setCellValue(pmo.getTitle());
		
		cell = row.createCell(++column_count);
		cell.setCellValue(pmo.getJournal_Name());
		
		cell = row.createCell(++column_count);
		cell.setCellValue(pmo.getPublished_month()+"/"+pmo.getPublished_year());
		
		cell = row.createCell(++column_count);
		cell.setCellValue(pmo.getAuthors().toString());
		
		cell = row.createCell(++column_count);
		cell.setCellValue(pmo.getGrants().toString());
		
//		cell = row.createCell(++column_count);
//		cell.setCellValue(pmo.getAbstract_Text());
		
	}
	
	public void persistToExcelFile(XSSFWorkbook workbook, File file, String FileName){
		try {
			FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath()+"/"+FileName); 
            workbook.write(outputStream);
		}catch (Exception e) {
			System.out.println("Could Not Write To File : "+FileName);
			e.printStackTrace();
		}
	}

}