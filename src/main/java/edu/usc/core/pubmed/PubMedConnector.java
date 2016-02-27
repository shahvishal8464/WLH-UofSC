package edu.usc.core.pubmed;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class PubMedConnector {
    HttpClient client;
    HttpPost post;
    PubMedDataParser pmdp = new PubMedDataParser();
    
    private void connect(String searchType){
    	
    	client  = new DefaultHttpClient(); 
    	post    = new HttpPost(ConnectionConstants.pubMedConnectionUrl + searchType);         
    }

    private HttpEntity getHttpResponse(List<NameValuePair> parameters){    	
    	HttpEntity entity = null;
    	try { 
            post.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8")); 
            HttpResponse resp = client.execute(post); 
            entity = resp.getEntity();
        } catch (UnsupportedEncodingException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        
        return entity;
    	
    }

    public List<String> getSearchResults(String Keyword){
    	connect(ConnectionConstants.SEARCH_URL);
        List<NameValuePair> parameters    = new ArrayList<NameValuePair>(3); 
        parameters.add(new BasicNameValuePair(ConnectionConstants.DB			, ConnectionConstants.PUBMED)); 
        parameters.add(new BasicNameValuePair(ConnectionConstants.TERM			, Keyword)); 
        parameters.add(new BasicNameValuePair(ConnectionConstants.RETURN_MODE	, ConnectionConstants.JSON)); 
        parameters.add(new BasicNameValuePair(ConnectionConstants.USEHISTORY	, "y")); 
        parameters.add(new BasicNameValuePair(ConnectionConstants.RETURN_MAX	, "1000"));
        parameters.add(new BasicNameValuePair(ConnectionConstants.WEB_ENV		, "gkdnz"));
        
        PubMedDataParser pmdp = new PubMedDataParser();
        return pmdp.parsePudMedIDs(getHttpResponse(parameters));
    }
    
    public PubMedObject fetchPubMedArticle(String id) { 
    	try{
	    	connect(ConnectionConstants.FETCH_URL);
	        List<NameValuePair> parameters    = new ArrayList<NameValuePair>(4); 
	        parameters.add(new BasicNameValuePair(ConnectionConstants.DB			, ConnectionConstants.PUBMED)); 
	        parameters.add(new BasicNameValuePair(ConnectionConstants.ID			, id)); 
	        parameters.add(new BasicNameValuePair(ConnectionConstants.RETURN_TYPE	, ConnectionConstants.ABSTRACT));
	        parameters.add(new BasicNameValuePair(ConnectionConstants.RETURN_MAX	, "5000"));
	        parameters.add(new BasicNameValuePair(ConnectionConstants.RETURN_MODE	, ConnectionConstants.XML));
	        parameters.add(new BasicNameValuePair(ConnectionConstants.WEB_ENV		, "gkdnz"));
	        
	        HttpEntity entity = getHttpResponse(parameters);
//	        System.out.println(EntityUtils.toString(entity));
//	        System.out.println("\n");
	        return pmdp.getPudMedObject(entity);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
        return null; 
    } 
    
    public String fetchAbstractText(String id) { 
    	String abstractText = null;
    	try{
	    	connect(ConnectionConstants.FETCH_URL);
	        List<NameValuePair> parameters    = new ArrayList<NameValuePair>(4); 
	        parameters.add(new BasicNameValuePair(ConnectionConstants.DB			, ConnectionConstants.PUBMED)); 
	        parameters.add(new BasicNameValuePair(ConnectionConstants.ID			, id)); 
	        parameters.add(new BasicNameValuePair(ConnectionConstants.VERSION		, ConnectionConstants.VERSION_NUM));
	        parameters.add(new BasicNameValuePair(ConnectionConstants.RETURN_TYPE	, ConnectionConstants.TEXT));
	        parameters.add(new BasicNameValuePair(ConnectionConstants.RETURN_MAX	, "1000"));
	        parameters.add(new BasicNameValuePair(ConnectionConstants.RETURN_MODE	, ConnectionConstants.ABSTRACT));
	        parameters.add(new BasicNameValuePair(ConnectionConstants.WEB_ENV		, "gkdnz"));
	        
	        HttpEntity entity = getHttpResponse(parameters);
	        abstractText = EntityUtils.toString(entity);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
        return abstractText; 
    } 

    public String getSummary(String id){         
    	connect(ConnectionConstants.SUMMARY_URL);
        List<NameValuePair> parameters    = new ArrayList<NameValuePair>(4); 
        parameters.add(new BasicNameValuePair(ConnectionConstants.DB			, ConnectionConstants.PUBMED)); 
        parameters.add(new BasicNameValuePair(ConnectionConstants.ID			, id)); 
        parameters.add(new BasicNameValuePair(ConnectionConstants.VERSION		, ConnectionConstants.VERSION_NUM));
        parameters.add(new BasicNameValuePair(ConnectionConstants.RETURN_MODE	, ConnectionConstants.JSON));
        parameters.add(new BasicNameValuePair(ConnectionConstants.RETURN_MAX	, "1000"));
        parameters.add(new BasicNameValuePair(ConnectionConstants.WEB_ENV		, "gkdnz"));
        
        HttpEntity entity = getHttpResponse(parameters);
        System.out.println(entity.toString());
        return entity.toString();
        		
    } 
}
