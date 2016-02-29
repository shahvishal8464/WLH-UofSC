package edu.usc.wlh.ERIC;

public class KeywordSearch {

	static String arr[] = {"aids","hiv","women","female", "health", "wellness","function", "integration"};
	private int i=0;
	private PersistERICDetails persist = new PersistERICDetails();
	public void searchKeywords(ERICObject object){
		String abstract_text= null;
		String title= null;
		
		if((abstract_text=object.getAbstract())!=null)
				abstract_text= abstract_text.toLowerCase();
		
		if((title= object.getTitle())!=null)
				title= title.toLowerCase();
			
		for(i=0; i<arr.length; i++){
			if(title!= null && title.contains(arr[i])){
				object.addKeywords(arr[i]);
			}
			if(abstract_text!= null && abstract_text.contains(arr[i])){
				object.addKeywords(arr[i]);
			}
		}
		
		if(object.getKeywords().size()>=2){
			persist.persistToWordDocument(object);
			persist.persistToTextFile(object);
			persist.addRowCommonExcelSheet(object);
			persist.addRowYearlyExcelSheet(object);
		}
	}
}
