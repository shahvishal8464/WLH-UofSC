package edu.usc.wlh.ERIC;

import java.util.ArrayList;
import java.util.List;

public class ERICObject {

	private String ERIC_ID;
	private String Title;
	private String Journal;
	private String Date_Published;
	private List<String> Authors;
	private String Abstract;
	private List<String> Keywords;
	
	
	public ERICObject(String eRIC_ID, String title, String journal,
			String date_Published, List<String> authors, String abstract1,
			List<String> keywords) {
		super();
		ERIC_ID = eRIC_ID;
		Title = title;
		Journal = journal;
		Date_Published = date_Published;
		Authors = authors;
		Abstract = abstract1;
		Keywords = keywords;
	}


	public ERICObject() {
		Authors = new ArrayList<String>();
		Keywords = new ArrayList<String>();
	}
	
	public void reInitialize(){
		ERIC_ID = "";
		Title = "";
		Journal = "";
		Date_Published = "";
		Authors.clear();
		Abstract = "";
		Keywords.clear();
	}


	/**
	 * @return the eRIC_ID
	 */
	public String getERIC_ID() {
		return ERIC_ID;
	}


	/**
	 * @param eRIC_ID the eRIC_ID to set
	 */
	public void setERIC_ID(String eRIC_ID) {
		ERIC_ID = eRIC_ID;
	}


	/**
	 * @return the title
	 */
	public String getTitle() {
		return Title;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		Title = title;
	}


	/**
	 * @return the journal
	 */
	public String getJournal() {
		return Journal;
	}


	/**
	 * @param journal the journal to set
	 */
	public void setJournal(String journal) {
		Journal = journal;
	}


	/**
	 * @return the date_Published
	 */
	public String getDate_Published() {
		return Date_Published;
	}


	/**
	 * @param date_Published the date_Published to set
	 */
	public void setDate_Published(String date_Published) {
		Date_Published = date_Published;
	}


	/**
	 * @return the authors
	 */
	public List<String> getAuthors() {
		return Authors;
	}


	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(List<String> authors) {
		Authors = authors;
	}

	
	/**
	 * @param authors the authors to set
	 */
	public void addAuthors(String author) {
		Authors.add(author);
	}


	/**
	 * @return the abstract
	 */
	public String getAbstract() {
		return Abstract;
	}


	/**
	 * @param abstract1 the abstract to set
	 */
	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}


	/**
	 * @return the keywords
	 */
	public List<String> getKeywords() {
		return Keywords;
	}


	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(List<String> keywords) {
		Keywords = keywords;
	}

	
	/**
	 * @param keywords the keywords to set
	 */

	public void addKeywords(String keyword) {
		if(Keywords.contains(keyword))
			return;
		else
			Keywords.add(keyword);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ERIC_ID=" + ERIC_ID + ", Title=" + Title
				+ ", Journal=" + Journal + ", Date_Published=" + Date_Published
				+ ", Authors=" + Authors + ", Abstract=" + Abstract
				+ ", Keywords=" + Keywords;
	}
	
		
}
