package edu.usc.core.ERIC;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class readERICDataset {

	public static void main(String[] args) {
		File dir=new File("ERIC Database");
		File[] file_list = dir.listFiles();
		PersistERICDetails persist = new PersistERICDetails();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
			SAXParser saxParser = factory.newSAXParser();
			ERICDataHandler erichandler = new ERICDataHandler();
			persist.createCommonExcelSheet();
			
			for(int i=1995; i<2017;i++){
				persist.createPath("docs/"+i);
				persist.createPath("text/"+i);
				persist.createYearlyExcelSheet();
			}
			
			for( File file : file_list){
				System.out.println("Reading File : "+file.getName());
				saxParser.parse(file, erichandler);
				System.out.println("Parsing of File Completed : "+file.getName());
				persist.persistYearlyExcelSheet(file.getName().replaceAll("\\D+",""));
			}
			persist.persistCommonExcelSheet();
        } catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
