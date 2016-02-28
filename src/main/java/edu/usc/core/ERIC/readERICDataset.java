package edu.usc.core.ERIC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class readERICDataset {

	public static void main(String[] args) {
		File dir=new File("ERIC Database");
		File[] file_list = dir.listFiles();
		PersistERICDetails persist = new PersistERICDetails();
		ERICDataParser parser = new ERICDataParser();
		

        InputStream is =null;
        try {
			persist.createCommonExcelSheet();
			
			for(int i=1995; i<2017;i++){
				persist.createPath("docs/"+i);
				persist.createPath("text/"+i);
			}
			
			for( File file : file_list){
				persist.createYearlyExcelSheet();
				System.out.println("Reading File : "+file.getName());
				is = new FileInputStream(file);
				parser.parse(is);
				System.out.println("Parsing of File Completed : "+file.getName());
				persist.persistYearlyExcelSheet(file.getName().replaceAll("\\D+",""));
			}
			persist.persistCommonExcelSheet();
        } catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getString(InputStream is){
		BufferedReader br = null;
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
