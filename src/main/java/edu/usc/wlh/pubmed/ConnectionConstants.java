package edu.usc.wlh.pubmed;

public class ConnectionConstants {

	public static final String pubMedConnectionUrl = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/";  
    public static final String SEARCH_URL = "esearch.fcgi?";
    public static final String SUMMARY_URL = "esummary.fcgi?";
    public static final String FETCH_URL = "efetch.fcgi?";
	
    // Connection Parameters
	public static final String ID			= "id";
	public static final String DB 			= "db";
	public static final String PUBMED 		= "pubmed";
	public static final String TERM 		= "term";
	public static final String RETURN_MODE	= "retmode";
	public static final String RETURN_MAX	= "retmax";
	public static final String RETURN_TYPE 	= "rettype";
	public static final String TEXT 		= "text";
	public static final String ABSTRACT		= "abstract";
	public static final String JSON			= "json";
	public static final String XML			= "xml";
	public static final String USEHISTORY	= "usehistory";
	public static final String WEB_ENV		= "WebEnv";
	public static final String VERSION		= "version";
	public static final String VERSION_NUM	= "2.0";

}
