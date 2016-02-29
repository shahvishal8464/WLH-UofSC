package edu.usc.wlh.CINAHL;

import java.util.ArrayList;
import java.util.List;

public class CINAHLObject {
		private String Paper_URL;
		private String UITerm;
		private String Journal;
		private String Date;
		private String Title;
		private List<String> Author;
		private List<String> Affiliation;
		private String Abstract;
		
		public CINAHLObject(String paper_URL, String uITerm, String journal,
				String date, String title, List<String> author,
				List<String> affiliation, String abstract1) {
			super();
			Paper_URL = paper_URL;
			UITerm = uITerm;
			Journal = journal;
			Date = date;
			Title = title;
			Author = author;
			Affiliation = affiliation;
			Abstract = abstract1;
		}

		public CINAHLObject() {
			super();
			Author = new ArrayList<>();
			Affiliation = new ArrayList<>();
		}

		public void reInitialize(){
			Paper_URL = "";
			UITerm = "";
			Journal = "";
			Date = "";
			Title = "";
			Author.clear();
			Affiliation.clear();
			Abstract = "";			
		}
		
		/**
		 * @return the paper_URL
		 */
		public String getPaper_URL() {
			return Paper_URL;
		}

		/**
		 * @param paper_URL the paper_URL to set
		 */
		public void setPaper_URL(String paper_URL) {
			Paper_URL = paper_URL;
		}

		/**
		 * @return the uITerm
		 */
		public String getUITerm() {
			return UITerm;
		}

		/**
		 * @param uITerm the uITerm to set
		 */
		public void setUITerm(String uITerm) {
			UITerm = uITerm;
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
		 * @return the date
		 */
		public String getDate() {
			return Date;
		}

		/**
		 * @param date the date to set
		 */
		public void setDate(String date) {
			Date = date;
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
		 * @return the author
		 */
		public List<String> getAuthor() {
			return Author;
		}

		/**
		 * @param author the author to set
		 */
		public void setAuthor(List<String> author) {
			Author = author;
		}

		/**
		 * @param author the author to add
		 */
		public void addAuthor(String author) {
			if(Author.contains(author))
				return;
			else
				Author.add(Author.size(), author);
		}

		/**
		 * @return the affiliation
		 */
		public List<String> getAffiliation() {
			return Affiliation;
		}

		/**
		 * @param affiliation the affiliation to set
		 */
		public void setAffiliation(List<String> affiliation) {
			Affiliation = affiliation;
		}

		/**
		 * @param author the author to affiliation
		 */
		public void addAffiliation(String affiliation) {
			if(Affiliation.contains(affiliation))
				return;
			else
				Affiliation.add(Affiliation.size(), affiliation);
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

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "CINAHLObject [Paper_URL=" + Paper_URL + ", UITerm="
					+ UITerm + ", Journal=" + Journal + ", Date=" + Date
					+ ", Title=" + Title + ", Author=" + Author
					+ ", Affiliation=" + Affiliation + ", Abstract=" + Abstract
					+ "]";
		}

		
}
