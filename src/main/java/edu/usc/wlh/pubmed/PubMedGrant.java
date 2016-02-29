package edu.usc.wlh.pubmed;

public class PubMedGrant {

	public String GrantID;
	public String Agency;
	public String Country;
	
	
	public PubMedGrant(String grantID, String agency,String country) {
		super();
		GrantID = grantID;
		Agency = agency;
		Country = country;
	}


	public PubMedGrant() {
		super();
	}


	/**
	 * @return the grantID
	 */
	public String getGrantID() {
		return GrantID;
	}


	/**
	 * @param grantID the grantID to set
	 */
	public void setGrantID(String grantID) {
		GrantID = grantID;
	}


	/**
	 * @return the agency
	 */
	public String getAgency() {
		return Agency;
	}


	/**
	 * @param agency the agency to set
	 */
	public void setAgency(String agency) {
		Agency = agency;
	}


	/**
	 * @return the country
	 */
	public String getCountry() {
		return Country;
	}


	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		Country = country;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[GrantID=" + GrantID + ", Agency=" + Agency+ ", Country=" + Country + "]";
	}

}
