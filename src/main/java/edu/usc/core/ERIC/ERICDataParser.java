package edu.usc.core.ERIC;

import java.io.InputStream;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class ERICDataParser {

	private KeywordSearch search= new KeywordSearch();
	private ERICObject object = new ERICObject();
	private String attribute = new String();
	
	
	public void parse(InputStream is){
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(is);
            
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()){
                    StartElement startElement = xmlEvent.asStartElement();
                 
                    if(startElement.getName().getLocalPart().equals("record")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    }
                    
                    else if(startElement.getName().getLocalPart().equals("creator")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	attribute = startElement.getAttributeByName(new QName("scheme")).toString();
            			if(attribute==null)
            				attribute = "";
            			else {
            				attribute = attribute.substring(attribute.indexOf('\'')).replaceAll("'", "");
            				if(attribute.equalsIgnoreCase("personal author"))
            					object.addAuthors(xmlEvent.asCharacters().getData());
            			}
                    }
                    
                    else if(startElement.getName().getLocalPart().equals("title")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	object.setTitle(xmlEvent.asCharacters().getData());
                    }
                    
                    else if(startElement.getName().getLocalPart().equals("identifier")){
                    	xmlEvent = xmlEventReader.nextEvent();                    	
                    	attribute = startElement.getAttributeByName(new QName("scheme")).toString();
            			if(attribute==null && object.getERIC_ID()==null)
            				object.setERIC_ID(null);
            			else{
            				attribute = attribute.substring(attribute.indexOf('\'')).replaceAll("'", "");
            				if(attribute.equalsIgnoreCase("eric_accno")){
            					object.setERIC_ID(xmlEvent.asCharacters().getData());
            				}
            			}
                    }
                    
                    else if(startElement.getName().getLocalPart().equals("description")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(xmlEvent.isEndElement())
                			object.setAbstract(null);
                    	else
                    		object.setAbstract(xmlEvent.asCharacters().getData());
                    }
                    
                    else if(startElement.getName().getLocalPart().equals("source")){
                    	xmlEvent = xmlEventReader.nextEvent();
                		if(!xmlEvent.isEndElement()){
                			object.setJournal(xmlEvent.asCharacters().getData());
                		}	
                    }
                    
                    else if(startElement.getName().getLocalPart().equals("citation")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(object.getJournal()==null || object.getJournal().isEmpty()){
                    		if(!xmlEvent.isEndElement()){
                    			object.setJournal(xmlEvent.asCharacters().getData());
                    		}	
                    	}	
                    }
                    
                    else if(startElement.getName().getLocalPart().equals("publisher")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	if(!xmlEvent.isEndElement()){
                    		if(object.getJournal()==null || object.getJournal().isEmpty()){
                    			object.setJournal(xmlEvent.asCharacters().getData());
                    		}
                    	}	
                    }
                    
                    else if(startElement.getName().getLocalPart().equals("issue")){
                    	xmlEvent = xmlEventReader.nextEvent();
                    	object.setDate_Published(xmlEvent.asCharacters().getData().replaceAll("RIE", "").replaceAll("CI",""));
                    }
                }

                else if(xmlEvent.isEndElement()){
                	EndElement end_element=xmlEvent.asEndElement();
                	if(end_element.getName().getLocalPart().equals("record")){
                		search.searchKeywords(object);
            			object.reInitialize();
                    }
                }
            }
		}catch(ClassCastException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
