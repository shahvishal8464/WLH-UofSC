package edu.usc.core.pubmed;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PubMedDataParser {

	private List<String> IDs = new ArrayList<String>();
	private JSONParser parser= new JSONParser();
	public static PubMedObject pmobj ; 
		
	public List<String> parsePudMedIDs(HttpEntity entity){
		try {
			JSONObject json_object = (JSONObject) parser.parse(EntityUtils.toString(entity));
	        JSONObject searchResult = (JSONObject) json_object.get("esearchresult");
	        JSONArray ids = (JSONArray)searchResult.get("idlist");
	        for (int i = 0; i < ids.size(); i++)
	        	IDs.add((String) ids.get(i));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return IDs;
	} 
	
	public PubMedObject getPudMedObject(HttpEntity entity){
		SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
			SAXParser saxParser = factory.newSAXParser();
			SAXHandler handler = new SAXHandler();
			InputSource is = new InputSource(new StringReader(EntityUtils.toString(entity)));
			saxParser.parse(is , handler);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return pmobj;
	}
}

class SAXHandler extends DefaultHandler{
	
	private boolean PubMed_Id;
	private boolean Journal_Name;
	private boolean Title;
	private boolean fore_name;
	private boolean last_name;
	private boolean Author_Affiliation;
	private boolean Abstract_Text;	
	private boolean Published_year;
	private boolean Published_month;
	private boolean is_author;
	private boolean date_Created;
	private boolean has_Grant;
	private boolean GrantID;
	private boolean Agency;
	private boolean Country;
	private boolean GrantList;
	
	private Author author;
	private PubMedObject pmo;
	private PubMedGrant grant;
	
	public void startDocument() throws SAXException {
		pmo = new PubMedObject();
    }

    public void endDocument() throws SAXException {
    	PubMedDataParser.pmobj = pmo;
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) 
        throws SAXException {
    	
    		if(qName.equalsIgnoreCase("AbstractText")){
    			Abstract_Text = true;
    		}else if(qName.equalsIgnoreCase("ArticleTitle")){
    			Title =true;
    		}else if(qName.equalsIgnoreCase("Title")){
				Journal_Name = true;
    		}else if(qName.equalsIgnoreCase("PMID")){
				PubMed_Id = true;
    		}else if(qName.equalsIgnoreCase("Author")){
				author = new Author();
				is_author = true;
    		}else if(qName.equalsIgnoreCase("LastName") && is_author){
				last_name = true;
    		}else if(qName.equalsIgnoreCase("ForeName") && is_author){
				fore_name = true;
    		}else if(qName.equalsIgnoreCase("Affiliation")){
    			Author_Affiliation= true;
    		}else if(qName.equalsIgnoreCase("PubMedPubDate")){
    			date_Created =true;
    		}else if(qName.equalsIgnoreCase("Month") && date_Created){
    			Published_month =true;
    		}else if(qName.equalsIgnoreCase("Year") && date_Created){
    			Published_year =true;
    		}else if(qName.equalsIgnoreCase("GrantList")){
    			GrantList =true;    			
    		}else if(qName.equalsIgnoreCase("Grant")){
    			grant = new PubMedGrant();
    			has_Grant= true;
    		}else if(qName.equalsIgnoreCase("GrantID") && has_Grant){
    			GrantID =true;
    		}else if(qName.equalsIgnoreCase("Agency")){
    			Agency =true;
    		}else if(qName.equalsIgnoreCase("Country") && GrantList){
    			Country =true;
    		} 
    }

    public void endElement(String namespaceURI, String localName, String qName)
        throws SAXException {
		if(qName.equalsIgnoreCase("AbstractText")){
			Abstract_Text = false;
		}else if(qName.equalsIgnoreCase("ArticleTitle")){
			Title =false;
		}else if(qName.equalsIgnoreCase("Title")){
			Journal_Name = false;
		}else if(qName.equalsIgnoreCase("PMID")){
			PubMed_Id = false;
		}else if(qName.equalsIgnoreCase("LastName") ){
			last_name = false;
		}else if(qName.equalsIgnoreCase("ForeName")){
			fore_name = false;
		}else if(qName.equalsIgnoreCase("Affiliation")){
			Author_Affiliation= false;
		}else if(qName.equalsIgnoreCase("Author")){
			is_author = false;
		}else if(qName.equalsIgnoreCase("PubMedPubDate")){
			date_Created =false;
		}else if(qName.equalsIgnoreCase("Month")){
			Published_month =false;
		}else if(qName.equalsIgnoreCase("Year")){
			Published_year =false;
		}else if(qName.equalsIgnoreCase("GrantList")){
			GrantList =false;    			
		}else if(qName.equalsIgnoreCase("Grant")){
			has_Grant= false;
		}else if(qName.equalsIgnoreCase("GrantID")){
			GrantID =false;
		}else if(qName.equalsIgnoreCase("Agency") ){
			Agency =false;
		}else if(qName.equalsIgnoreCase("Country")){
			Country =false;
		}
    }

    public void characters(char ch[], int start, int length) {
    	if(Abstract_Text){
    		pmo.setAbstract_Text(new String(ch, start, length));
    		Abstract_Text = false;
    	}else if(Title){
    		pmo.setTitle(new String(ch, start, length));
    		Title = false;
    	}else if(Published_year){
    		pmo.setPublished_year(new String(ch, start, length));
    		Published_year = false;
    	}else if(Published_month){
    		pmo.setPublished_month(new String(ch, start, length));
    		Published_month = false;
    	}else if(Journal_Name){
    		pmo.setJournal_Name(new String(ch, start, length));
    		Journal_Name = false;	
    	}else if(PubMed_Id){
    		pmo.setPubMed_Id(new String(ch, start, length));
    		PubMed_Id = false;	
    	}else if(Author_Affiliation){
    		author.setAffiliation(new String(ch, start, length));
    		Author_Affiliation= false;
		}else if(last_name && is_author){
			author.setLast_name(new String(ch, start, length));
			last_name = false;
		}else if(fore_name && is_author){
			author.setFore_name(new String(ch, start, length));
			fore_name = false;
		}else if(is_author){			
			pmo.addAuthor(author);
			is_author = false;
		}else if(GrantID){	
			grant.setGrantID(new String(ch, start, length));
			GrantID = false;
		}else if(Agency){		
			grant.setAgency(new String(ch, start, length));
			Agency = false;
		}else if(Country && GrantList){
			grant.setCountry(new String(ch, start, length));
			Country = false;
		}else if(has_Grant){
			pmo.addGrant(grant);
			has_Grant = false;
		}else if(GrantList){
			GrantList =false;    			
		}
    }
}