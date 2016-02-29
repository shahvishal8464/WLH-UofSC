package edu.usc.wlh.pubmed;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PubMedDataParser {

	private List<String> IDs = new ArrayList<String>();
	private JSONParser parser= new JSONParser();
	public static PubMedObject pmobj =new PubMedObject(); 
	private static boolean has_pubmed_date; 
	private static boolean has_grant;
	private static boolean PubMedDate=true;
	private PubMedGrant grant=null;
	private Author author=null;
	private static boolean PMID = true;
	
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

		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE); 
		
		try {
			InputStream is = new ByteArrayInputStream(EntityUtils.toString(entity).getBytes());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(is);
            PMID = true;
            PubMedDate = true;
            pmobj.getAuthors().clear();
            pmobj.getGrants().clear();
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                	
                if (xmlEvent.isStartElement()){
                    StartElement startElement = xmlEvent.asStartElement();
                 
                    if(startElement.getName().getLocalPart().equals("PubMedPubDate") && PubMedDate){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(!xmlEvent.isEndElement())
                    		has_pubmed_date=true;
                    	else 
                    		has_pubmed_date=false;
                    }
                    else if(startElement.getName().getLocalPart().equals("Year") && has_pubmed_date){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(!xmlEvent.isEndElement())
                    		pmobj.setPublished_year(xmlEvent.asCharacters().getData());
                    	else
                    		pmobj.setPublished_year("");
                    }
                    else if(startElement.getName().getLocalPart().equals("Month") && has_pubmed_date){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(!xmlEvent.isEndElement())
                    		pmobj.setPublished_month(xmlEvent.asCharacters().getData());
                    	else
                    		pmobj.setPublished_month("");
                    }
                    else if(startElement.getName().getLocalPart().equals("AbstractText")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(!xmlEvent.isEndElement())
                    		pmobj.setAbstract_Text(xmlEvent.asCharacters().getData());
                    	else
                    		pmobj.setAbstract_Text("");
                    }
                    else if(startElement.getName().getLocalPart().equals("ArticleTitle")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(!xmlEvent.isEndElement())
                    		pmobj.setTitle(xmlEvent.asCharacters().getData());
                    	else
                    		pmobj.setTitle("");
                    }
                    else if(startElement.getName().getLocalPart().equals("Title")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(!xmlEvent.isEndElement())
                    		pmobj.setJournal_Name(xmlEvent.asCharacters().getData());
                    	else
                    		pmobj.setJournal_Name("");
                    }
                    else if(startElement.getName().getLocalPart().equals("PMID") && PMID){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(!xmlEvent.isEndElement())
                    		pmobj.setPubMed_Id(xmlEvent.asCharacters().getData());
                    	else
                    		pmobj.setPubMed_Id("");

                    		PMID= false;
                    }
                    else if(startElement.getName().getLocalPart().equals("Author")){
                    	author=new Author();
                    	xmlEvent = xmlEventReader.nextEvent();
                    }
                    else if(startElement.getName().getLocalPart().equals("LastName")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(!xmlEvent.isEndElement())
                    		author.setLast_name(xmlEvent.asCharacters().getData());
                    	else
                    		author.setLast_name("");
                    }
                    else if(startElement.getName().getLocalPart().equals("ForeName")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(!xmlEvent.isEndElement())
                    		author.setFore_name(xmlEvent.asCharacters().getData());
                    	else
                    		author.setFore_name("");
                    }
                    else if(startElement.getName().getLocalPart().equals("Affiliation")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(!xmlEvent.isEndElement())
                    		author.setAffiliation(xmlEvent.asCharacters().getData());
                    	else
                    		author.setAffiliation("");
                    }
                    else if(startElement.getName().getLocalPart().equals("Grant") ){
            			grant = new PubMedGrant();
            			has_grant =true;
                    	xmlEvent = xmlEventReader.nextEvent();
                    }
                    else if(startElement.getName().getLocalPart().equals("GrantID") && has_grant ){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(!xmlEvent.isEndElement())
                    		grant.setGrantID(xmlEvent.asCharacters().getData());
                    	else
                    		grant.setGrantID("");
                    }
                    else if(startElement.getName().getLocalPart().equals("Agency") && has_grant ){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(!xmlEvent.isEndElement())
                    		grant.setAgency(xmlEvent.asCharacters().getData());
                    	else
                    		grant.setAgency("");
                    }
                    else if(startElement.getName().getLocalPart().equals("Country") && has_grant ){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(!xmlEvent.isEndElement())
                    		grant.setCountry(xmlEvent.asCharacters().getData());
                    	else
                    		grant.setCountry("");
                    }
                }
                else if (xmlEvent.isEndElement()){
                	EndElement endElement = xmlEvent.asEndElement();
                	if(endElement.getName().getLocalPart().equals("PubMedPubDate")){
                		has_pubmed_date = false;
                		PubMedDate = false;
                	}
                	else if(endElement.getName().getLocalPart().equals("Grant")){
                		pmobj.addGrant(grant);
                		has_grant=false;
                	}
                	else if(endElement.getName().getLocalPart().equals("Author")){
                		pmobj.addAuthor(author);
                	}
                }
            } 
        }catch (XMLStreamException | ParseException | IOException e) {
                e.printStackTrace();
        }
		
		return pmobj;
	}
}
