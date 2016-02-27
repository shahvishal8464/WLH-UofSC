package edu.usc.core.CINAHL;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;



public class CINAHLConnector {
   private CINAHLConnectionConstants cinahl_connector = new CINAHLConnectionConstants();
   
   public InputStream fetchArticles(int num){
	   try {
		   
		   URL url = new URL(cinahl_connector.buildDefaultURL(num));
		   URLConnection connection = url.openConnection();
		   return connection.getInputStream();
		   
	   } catch (MalformedURLException e) {
		   e.printStackTrace();
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
	   return null;
   }
}