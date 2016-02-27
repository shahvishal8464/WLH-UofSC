package edu.usc.core.pubmed;

public class QueryBuilder {

	public String buildURL(String[] term){
		
		String str="(";

		for(int i=0; i<term.length ; i++)
		{
			if(i!= term.length-1)
				str=str+"\""+term[i]+"\"[All Fields] AND ";
			else
				str=str+"\""+term[i]+"\"[All Fields])";
		}
		return str;
	}
	
	public String buildDefaultURL(String start, String end){
/*		String str="(\"HIV\"[All Fields] OR \"Aids\"[All Fields]) AND"+
					"(\"women\"[All Fields] OR \"female\"[All Fields]) AND"+
					"(\"Wellness\"[All Fields] OR \"Function\"[All Fields] OR \"Health\"[All Fields] OR \"Integration\"[All Fields]) AND"+
					"(\""+start+"\"[Date - Create] : \""+end+"\"[Date - Create]) ";
*/	
		String str="(HIV[Title/Abstract] OR Aids[Title/Abstract])"+
					" AND (women[Title/Abstract] OR female[Title/Abstract])"+
					" AND (wellness[Title/Abstract] OR function[Title/Abstract] OR Health[Title/Abstract] OR Integration[Title/Abstract])"+
					" AND (\""+start+"\"[Date - Create] : \""+end+"\"[Date - Create])" ;
		return str;
	}
}
