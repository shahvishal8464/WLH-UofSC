package edu.usc.core.pubmed;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PubMedObject {

	private String Published_year;
	private String Published_month;
	private String PubMed_Id;
	private String Journal_Name;
	private String Title;
	private List<Author> authors = new ArrayList<Author>() ;
	private String Abstract_Text;
	private List<PubMedGrant> Grants = new ArrayList<PubMedGrant>();
	
	
	public PubMedObject(String published_year, String published_month,
			String pubMed_Id, String journal_Name, String title,
			List<Author> authors, String abstract_Text, List<PubMedGrant> grants) {
		super();
		Published_year = published_year;
		Published_month = published_month;
		PubMed_Id = pubMed_Id;
		Journal_Name = journal_Name;
		Title = title;
		this.authors = authors;
		Abstract_Text = abstract_Text;
		Grants = grants;
	}

	
	public PubMedObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the published_year
	 */
	public String getPublished_year() {
		return Published_year;
	}

	/**
	 * @param published_year the published_year to set
	 */
	public void setPublished_year(String published_year) {
		Published_year = published_year;
	}

	/**
	 * @return the published_month
	 */
	public String getPublished_month() {
		return Published_month;
	}

	/**
	 * @param published_month the published_month to set
	 */
	public void setPublished_month(String published_month) {
		Published_month = published_month;
	}

	/**
	 * @return the pubMed_Id
	 */
	public String getPubMed_Id() {
		return PubMed_Id;
	}

	/**
	 * @param pubMed_Id the pubMed_Id to set
	 */
	public void setPubMed_Id(String pubMed_Id) {
		PubMed_Id = pubMed_Id;
	}

	/**
	 * @return the journal_Name
	 */
	public String getJournal_Name() {
		return Journal_Name;
	}

	/**
	 * @param journal_Name the journal_Name to set
	 */
	public void setJournal_Name(String journal_Name) {
		Journal_Name = journal_Name;
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
	 * @return the authors
	 */
	public List<Author> getAuthors() {
		return authors;
	}

	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public void addAuthor(Author author){
		this.authors.add(author);
	}
	
	public Author createAuthor(){
		return new Author();
	}
	/**
	 * @return the abstract_Text
	 */
	public String getAbstract_Text() {
		return Abstract_Text;
	}

	/**
	 * @param abstract_Text the abstract_Text to set
	 */
	public void setAbstract_Text(String abstract_Text) {
		Abstract_Text = abstract_Text;
	}

	/**
	 * @return the grants
	 */
	public List<PubMedGrant> getGrants() {
		return Grants;
	}


	/**
	 * @param grants the grants to set
	 */
	public void setGrants(List<PubMedGrant> grants) {
		Grants = grants;
	}

	public void addGrant(PubMedGrant grant){
		this.Grants.add(grant);
	} 
	
	public PubMedGrant createGrant(){
		return new PubMedGrant();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PubMedObject [Published_year=" + Published_year
				+ ", Published_month=" + Published_month + ", PubMed_Id="
				+ PubMed_Id + ", Journal_Name=" + Journal_Name + ", Title="
				+ Title + ", authors=" + authors + ", Abstract_Text="
				+ Abstract_Text + ", Grants=" + Grants + "]";
	}
	
	
}