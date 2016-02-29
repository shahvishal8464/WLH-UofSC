package edu.usc.wlh.CINAHL;

import java.io.InputStream;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class CINAHLStaxParser {

	private int num_records=1;
	private String attribute=null;
	private String year=new String();
	private String month=new String();
	private boolean result = true;
	private CINAHLObject object=new CINAHLObject();
	private PersistCINAHLData persist = new PersistCINAHLData();
	
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
                    else if(startElement.getName().getLocalPart().equals("header")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	attribute = startElement.getAttributeByName(new QName("uiTerm")).toString();
            			if(attribute==null)
            				attribute = "";
            			else
            				object.setUITerm(attribute.substring(attribute.indexOf('=')+1).replaceAll("'", ""));
                    }
                    else if(startElement.getName().getLocalPart().equals("rec")){
                    	num_records++;
                    }
                    else if(startElement.getName().getLocalPart().equals("plink")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	object.setPaper_URL(xmlEvent.asCharacters().getData());
                    }
                    else if(startElement.getName().getLocalPart().equals("jtl")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	object.setJournal(xmlEvent.asCharacters().getData());
                    }
                    else if(startElement.getName().getLocalPart().equals("dt")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	attribute = startElement.getAttributeByName(new QName("year")).toString();
            			if(attribute==null)
            				attribute = "";
            			else
            				year = attribute.substring(attribute.indexOf('=')+1).replaceAll("'", "");
            			
            			attribute = startElement.getAttributeByName(new QName("month")).toString();
            			if(attribute==null)
            				attribute = "";
            			else
            				month = attribute.substring(attribute.indexOf('=')+1).replaceAll("'", "");
            			object.setDate(month+"/"+year);
                    }
                    else if(startElement.getName().getLocalPart().equals("atl")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	object.setTitle(xmlEvent.asCharacters().getData());
                    }
                    else if(startElement.getName().getLocalPart().equals("affil")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	object.addAffiliation(xmlEvent.asCharacters().getData());
                    }
                    else if(startElement.getName().getLocalPart().equals("au")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	object.addAuthor(xmlEvent.asCharacters().getData());
                    }
                    else if(startElement.getName().getLocalPart().equals("ab")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	object.setAbstract(xmlEvent.asCharacters().getData());
                    }
                }
                else if (xmlEvent.isEndElement()){
                	EndElement endElement = xmlEvent.asEndElement();
                	if(endElement.getName().getLocalPart().equals("rec")){
                		result = persist.containsUITerm(object.getUITerm());
                		if(!result);
                			persist.persistToTextFile(object);
                			persist.persistToWordDocument(object);
                			persist.addRowCommonExcelSheet(object);;
                		object.reInitialize();
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