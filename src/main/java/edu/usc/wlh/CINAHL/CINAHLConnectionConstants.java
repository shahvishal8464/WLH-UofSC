package edu.usc.wlh.CINAHL;

import java.io.FileInputStream;
import java.util.Properties;

public class CINAHLConnectionConstants {

	private String SEARCH_TERMS="&query=";
	private String DATABASE = "&db=";
	private String URL;

	private static final String CINAHLConnectionUrl ="http://eit.ebscohost.com/Services/SearchService.asmx";
	private static final String SEARCH_URL = "/Search?";
    
	private static final String NUM_RECORDS	= "&numrec=200";
	private static String START_RECORD	= "&startrec=";
    
	private static String PROFILE="&prof=";
	private static String PASSWORD="&pwd=";
    
    public static void readCredentials(){
    	Properties property = new Properties();
    	try {
    		
    		property.load(new FileInputStream( "resources/config.properties"));
    		PROFILE += property.getProperty("PROFILE");
    		PASSWORD += property.getProperty("PASSWORD");
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public String buildDefaultURL(int num){
    	URL= CINAHLConnectionUrl + SEARCH_URL;
    	URL+=PROFILE+PASSWORD+NUM_RECORDS;
    	URL+=SEARCH_TERMS+"((HIV+OR+AIDS)+AND+(Women+OR+female)+AND+(wellness+OR+Function+OR+health+OR+integration))";
    	URL+=DATABASE+"ccm"+DATABASE+"rzh";
    	URL+=START_RECORD+num;
    	return URL;
    } 
}
