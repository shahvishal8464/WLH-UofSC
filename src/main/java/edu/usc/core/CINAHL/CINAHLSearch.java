package edu.usc.core.CINAHL;

import java.io.InputStream;

public class CINAHLSearch {

	public static void main(String[] args) {
		CINAHLConnectionConstants.readCredentials() ;
		CINAHLConnector connector =new CINAHLConnector();
		CINAHLStaxParser parser = new CINAHLStaxParser();

		InputStream is = null;
		int i=1;
			
		while(parser.getNumRecords()>0){
				is=connector.fetchArticles(i);
				parser.parse(is);
				i+=200;
		}
	}	
}
