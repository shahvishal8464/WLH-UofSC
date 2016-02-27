package edu.usc.core.CINAHL;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CINAHLDataHandler extends DefaultHandler{

	private int num_records = 1;

	private boolean paper_link;
	private boolean article_title;
	private boolean journal_title;
	private boolean affiliation;
	private boolean abstract_text;
	private boolean date;
	private boolean publication_info;
	private boolean author;
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		if(qName.equalsIgnoreCase("rec")){
			System.out.println();
		}else if(qName.equalsIgnoreCase("plink")){
			paper_link = false;
		}else if(qName.equalsIgnoreCase("jtl")){
			journal_title = false;
		}else if(qName.equalsIgnoreCase("pubinfo")){
			publication_info = false;
		}else if(qName.equalsIgnoreCase("dt")){
			date= false;
		}else if(qName.equalsIgnoreCase("atl")){
			article_title = false;
		}else if(qName.equalsIgnoreCase("affil")){
			affiliation = false;
		}else if(qName.equalsIgnoreCase("au")){
			author = false;
		}else if(qName.equalsIgnoreCase("ab")){
			abstract_text = false;
		}
	}

	@Override
	public void startDocument() throws SAXException {
		num_records = 0;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if(qName.equalsIgnoreCase("rec")){
			num_records++;
		}else if(qName.equalsIgnoreCase("plink")){
			paper_link = true;
		}else if(qName.equalsIgnoreCase("jtl")){
			journal_title = true;
		}else if(qName.equalsIgnoreCase("pubinfo")){
			publication_info = true;
		}else if(qName.equalsIgnoreCase("dt")){
			date= true;
		}else if(qName.equalsIgnoreCase("atl")){
			article_title = true;
		}else if(qName.equalsIgnoreCase("affil")){
			affiliation = true;
		}else if(qName.equalsIgnoreCase("au")){
			author = true;
		}else if(qName.equalsIgnoreCase("ab")){
			abstract_text = true;
		}

	}
	
    public void characters(char ch[], int start, int length) {
    	if(paper_link){
    		System.out.println("PaperLink : "+new String(ch, start, length));
    		paper_link=false;
    	}else if(article_title){
    		System.out.println("Article Title : "+new String(ch, start, length));
    		article_title=false;
    	}else if(publication_info){
    		publication_info=false;
    	}else if(date){
    		System.out.println("Publication Date : "+new String(ch, start, length));
    		date=false;
    	}else if(affiliation){
    		System.out.println("Affiliation : "+new String(ch, start, length));
    		affiliation = false;
    	}else if(journal_title){
    		System.out.println("Journal : "+new String(ch, start, length));
    		journal_title = false;
		}else if(author){
			System.out.println("Author : "+new String(ch, start, length));
			author = false;
    	}else if(abstract_text){
    		System.out.println("Abstract : "+new String(ch, start, length));
    		abstract_text=false;
    	}
    }	
	
	public int getNumRecords(){
		return num_records;
	}	
}
