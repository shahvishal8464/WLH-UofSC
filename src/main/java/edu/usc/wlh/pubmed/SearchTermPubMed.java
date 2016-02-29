package edu.usc.wlh.pubmed;

import java.io.File;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SearchTermPubMed {

	public static void main(String[] args) {
		try{
			QueryBuilder qb= new QueryBuilder();		
			PubMedConnector pmc= new PubMedConnector();
			PersistResults persist = new PersistResults();
			PubMedObject pmo ;
			String abstract_text;
			
			File file1=null, file2=null, file3=null;
			file1=persist.createPath("pubmed", file1);
			
			XSSFWorkbook workbook = persist.createExcelWorkBook();
			XSSFSheet sheet = persist.createExcelSheet(workbook, "Publications");
			int row_count_common=0;
			int row_count_yearly=0;
			
			for(int i=1997; i<2017;i++){
				file2=persist.createPath("pubmed/docs/"+i, file2);
				file3=persist.createPath("pubmed/txt/"+i, file3);
				
				XSSFWorkbook yearly_workbook = persist.createExcelWorkBook();				
				XSSFSheet yearly_sheet = persist.createExcelSheet(yearly_workbook, i+"");
				row_count_yearly=0;
				for(int j=1; j<13; j++){				
					List<String> ids= pmc.getSearchResults(qb.buildDefaultURL(i+"/"+j, i+"/"+j));
					for(int k=0;k<ids.size(); k++,row_count_common++, row_count_yearly++){						
						pmo = pmc.fetchPubMedArticle(ids.get(k));
						abstract_text = pmc.fetchAbstractText(ids.get(k));
						
						persist.addRow(sheet, pmo, row_count_common);
						persist.addRow(yearly_sheet, pmo, row_count_yearly);
						persist.persistToWordDocument(abstract_text, file2, ids.get(k));
						persist.persistToTextFile(abstract_text, file3, ids.get(k));
					}
				}
				persist.persistToExcelFile(yearly_workbook,file1, "Publications-"+i+".xlsx");
				System.out.println("Written Yearly Publications to file : Publications-"+i+".xlsx");
			}
			persist.persistToExcelFile(workbook, file1,"Publications.xlsx");
			
			System.out.println("Writing To files completed");
			System.out.println("Terminating The Program");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}