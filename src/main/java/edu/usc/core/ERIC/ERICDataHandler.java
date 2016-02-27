package edu.usc.core.ERIC;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ERICDataHandler extends DefaultHandler{

	private KeywordSearch search= new KeywordSearch();
	private ERICObject object = new ERICObject();
	private boolean has_record;
	private boolean has_ID;
	private boolean has_Title;
	private boolean has_Journal;
	private boolean has_publication_date;
	private boolean has_author;
	private boolean has_abstract;
	private boolean has_keywords;
	private String attribute; 
	private boolean has_source;
	private boolean has_publisher;
	
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) 
        throws SAXException {
    	
    		if(qName.equalsIgnoreCase("record")){
    			has_record = true;
    		}else if(qName.equalsIgnoreCase("dc:creator")){
    			attribute = attrs.getValue("scheme");
    			if(attribute==null)
    				attribute = "";
    			has_author =true;
    		}else if(qName.equalsIgnoreCase("dc:title")){
    			has_Title =true;
    		}else if(qName.equalsIgnoreCase("dc:identifier")){
    			has_ID =true;
    		}else if(qName.equalsIgnoreCase("dc:description")){
    			has_abstract =true;
    		}else if(qName.equalsIgnoreCase("eric:citation")){
    			has_Journal =true;
    		}else if(qName.equalsIgnoreCase("dc:source")){
    			has_source =true;
    		}else if(qName.equalsIgnoreCase("dc:publisher")){
    			has_publisher =true;
    		}else if(qName.equalsIgnoreCase("eric:issue")){
    			has_publication_date =true;
    		}else if(qName.equalsIgnoreCase("eric:keywords")){
    			has_keywords =true;
    		}
    }

    public void endElement(String namespaceURI, String localName, String qName)
        throws SAXException {
		if(qName.equalsIgnoreCase("record")){
			has_record = false;
		}else if(qName.equalsIgnoreCase("dc:creator")){
			has_author =false;
		}else if(qName.equalsIgnoreCase("dc:title")){
			has_Title =false;
		}else if(qName.equalsIgnoreCase("dc:identifier")){
			has_ID =false;
		}else if(qName.equalsIgnoreCase("dc:description")){
			has_abstract =false;
		}else if(qName.equalsIgnoreCase("eric:citation")){
			has_Journal =false;
		}else if(qName.equalsIgnoreCase("dc:source")){
			has_source =false;
		}else if(qName.equalsIgnoreCase("dc:publisher")){
			has_publisher =false;
		}else if(qName.equalsIgnoreCase("eric:issue")){
			has_publication_date =false;
		}else if(qName.equalsIgnoreCase("eric:keywords")){
			has_keywords =false;
		} 
    }

    public void characters(char ch[], int start, int length) {
    	if(has_record){
    		search.searchKeywords(object);
			object.reInitialize();
    		has_record = false;
    	}else if(has_author && attribute.equalsIgnoreCase("personal author")){
    		object.addAuthors(new String(ch, start, length));
    		has_author = false;
    	}else if(has_Title){
    		object.setTitle(new String(ch, start, length));
    		has_Title = false;
    	}else if(has_abstract){
    		object.setAbstract(new String(ch, start, length));
    		has_abstract = false;
    	}else if(has_Journal){
    		if(object.getJournal()==null || object.getJournal().isEmpty())
    			object.setJournal(new String(ch, start, length));
    		has_Journal = false;
    	}else if(has_ID){
    		object.setERIC_ID(new String(ch, start, length));
    		has_ID = false;
    	}else if(has_source){
    		if(object.getJournal()==null || object.getJournal().isEmpty())
    			object.setJournal(new String(ch, start, length));
    		has_source = false;
    	}else if(has_publisher){
    			object.setJournal(new String(ch, start, length));
    		has_publisher = false;
    	}else if(has_publication_date){
			object.setDate_Published(new String(ch, start, length).replaceAll("RIE", ""));
			has_publication_date = false;
    	}else if(has_keywords){
			has_keywords= false;
    	}	
    }
    
}