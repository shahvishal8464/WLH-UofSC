package edu.usc.core.CINAHL;

import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class CINAHLStaxParser {

	int num_records=1;
	
	public void parse(InputStream is){
	
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE); 
        
		try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(is);
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                
                if (xmlEvent.isStartElement()){
                    StartElement startElement = xmlEvent.asStartElement();
                 
                    if(startElement.getName().getLocalPart().equals("records")){
                    	num_records=-1;
                    }
                    else if(startElement.getName().getLocalPart().equals("rec")){
                    	num_records++;
                    }
                    else if(startElement.getName().getLocalPart().equals("plink")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	System.out.println("Paper URL : "+xmlEvent.asCharacters().getData());
                    }
                    else if(startElement.getName().getLocalPart().equals("jtl")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	System.out.println("Journal : "+xmlEvent.asCharacters().getData());
                    }
                    else if(startElement.getName().getLocalPart().equals("dt")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	System.out.println("Date : "+xmlEvent.asCharacters().getData());
                    }
                    else if(startElement.getName().getLocalPart().equals("atl")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	System.out.println("Title : "+xmlEvent.asCharacters().getData());
                    }
                    else if(startElement.getName().getLocalPart().equals("affil")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	System.out.println("Affiliation : "+xmlEvent.asCharacters().getData());
                    }
                    else if(startElement.getName().getLocalPart().equals("au")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	System.out.println("Author : "+xmlEvent.asCharacters().getData());
                    }
                    else if(startElement.getName().getLocalPart().equals("ab")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	System.out.println("Abstract : "+xmlEvent.asCharacters().getData());
                    }
                }                    
            } 
        }catch (XMLStreamException e) {
                e.printStackTrace();
        }
	}
	
	public int getNumRecords(){
		return num_records;
	}
}
