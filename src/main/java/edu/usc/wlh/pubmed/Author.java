package edu.usc.wlh.pubmed;

public class Author {
	
		private String fore_name;
		private String last_name;
		private String affiliation;
		
		
		public Author(String fore_name, String last_name, String affiliation) {
			super();
			this.fore_name = fore_name;
			this.last_name = last_name;
			this.affiliation = affiliation;
		}

		public Author() {
			super();
		}
		
		
		/**
		 * @return the fore_name
		 */
		public String getFore_name() {
			return fore_name;
		}

		/**
		 * @param fore_name the fore_name to set
		 */
		public void setFore_name(String fore_name) {
			this.fore_name = fore_name;
		}

		/**
		 * @return the last_name
		 */
		public String getLast_name() {
			return last_name;
		}

		/**
		 * @param last_name the last_name to set
		 */
		public void setLast_name(String last_name) {
			this.last_name = last_name;
		}

		/**
		 * @return the affiliation
		 */
		public String getAffiliation() {
			return affiliation;
		}

		/**
		 * @param affiliation the affiliation to set
		 */
		public void setAffiliation(String affiliation) {
			this.affiliation = affiliation;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "[Name=" + fore_name + ", "+ last_name + ", affiliation=" + affiliation+"] ";
		}
		
		
}
