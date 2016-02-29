package edu.usc.core.CINAHL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;


public class PersistCINAHLData {

	private List<String> UITerms = new ArrayList<>();
	private static String abstractFilePath = "abstracts/CINAHL/";
	private static int column_count=0;
	private static int row_count=0;

	
	private File file = null; 
	private static XSSFWorkbook common_workbook= null;
	private static XSSFSheet common_sheet=null;
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
	
	public boolean containsUITerm(String UITerm){
		if(UITerms.contains(UITerm))
			return true;
		else{
			UITerms.add(UITerm);
			return false;
		}
	}
	
	public void persistToWordDocument(CINAHLObject object){
		XWPFDocument document= new XWPFDocument();
		FileOutputStream out;
		try {
			this.createPath("/docs/"+object.getDate().substring(object.getDate().indexOf('/')+1));
			out = new FileOutputStream( new File(abstractFilePath+"/docs/"+object.getDate().substring(object.getDate().indexOf('/')+1)+"/"+object.getUITerm()+".docx"));
			XWPFParagraph paragraph = document.createParagraph();
			
			XWPFRun run=paragraph.createRun();
			run.setFontSize(12);
			run.setText("Journal : "+object.getJournal() + " : "+ object.getDate());
			run.addBreak();
			
			XWPFRun run_1=paragraph.createRun();
			run_1.setFontSize(12);
			run_1.setText("URL : "+object.getPaper_URL());
			run_1.addBreak();
			
			XWPFRun run_2=paragraph.createRun();
			run_2.setBold(true);
			run_2.setFontSize(16);
			run_2.setText(object.getTitle());
			run_2.addBreak();

			XWPFRun run_4=paragraph.createRun();
			run_4.setFontSize(12);
			run_4.setText("UITerm : "+object.getUITerm());
			run_4.addBreak();

			XWPFRun run_3=paragraph.createRun();
			run_3.setFontSize(12);
			run_3.setText("Authors : "+object.getAuthor().toString());
			run_3.addBreak();			
			
			XWPFRun run_5=paragraph.createRun();
			run_5.setFontSize(12);
			run_5.setText("Affiliation : "+object.getAffiliation().toString());
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

	public void persistToTextFile(CINAHLObject object){
		File outputFile;
		BufferedWriter writer;
		try {
			this.createPath("/txt/"+object.getDate().substring(object.getDate().indexOf('/')+1));
			outputFile = new File(abstractFilePath+"/txt/"+object.getDate().substring(object.getDate().indexOf('/')+1)+"/"+object.getUITerm()+".txt");

			if (!outputFile.exists()){
				writer = new BufferedWriter(new FileWriter(outputFile));
				writer.write("ERIC_ID : "+object.getUITerm()+"\n");
				writer.write("Paper URL : "+object.getPaper_URL()+"\n");
				writer.write("Title : "+ object.getTitle()+"\n");
				writer.write("Journal : "+ object.getJournal()+"\n");
				writer.write("Publication Date : "+ object.getDate()+"\n");
				writer.write("Authors : "+ object.getAuthor()+"\n");
				writer.write("Affiliation : "+object.getAffiliation()+"\n");
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
		row_count=0;
	}

	public void addRowCommonExcelSheet(CINAHLObject object){
		row = common_sheet.createRow(row_count++);
		column_count = 0;
		
		Cell cell = row.createCell(column_count ++);
		cell.setCellValue(object.getUITerm());
		
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getTitle());
				
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getAuthor().toString());
		
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getAffiliation().toString());

		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getJournal());
		
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getDate());
		
		cell = row.createCell(column_count ++);
		cell.setCellValue(object.getPaper_URL());
	}
	
	public void persistCommonExcelSheet(){
		try {
			FileOutputStream outputStream = new FileOutputStream(abstractFilePath+"/CINAHL_Publications.xlsx"); 
            common_workbook.write(outputStream);
            System.out.println("Written "+ row_count+" Records To File : CINAHL_Publications.xlsx");
		}catch (Exception e) {
			System.out.println("Could Not Write To File : CINAHL_Publications.xlsx");
			e.printStackTrace();
		}
	}

}