/**
 * 
 */
package org.jared.android.volley.model;

/**
 * Contact d'une Žquipe
 * @author eric.taix@gmail.com
 */
public class ContactEquipe implements Contact {

	private String mail;
	private String mobile;
	private String telephone;
	private String nom;
	
	/* (non-Javadoc)
	 * @see org.jared.android.volley.model.Contact#getMail()
	 */
	@Override
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/* (non-Javadoc)
	 * @see org.jared.android.volley.model.Contact#getMobile()
	 */
	@Override
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/* (non-Javadoc)
	 * @see org.jared.android.volley.model.Contact#getTelephone()
	 */
	@Override
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/* (non-Javadoc)
	 * @see org.jared.android.volley.model.Contact#getNom()
	 */
	@Override
	public String getNom() {
		return nom;
	}
	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

}
