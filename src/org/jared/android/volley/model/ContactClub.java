/**
 * 
 */
package org.jared.android.volley.model;

/**
 * Adapteur entre un club et l'interface contact 
 * @author eric.taix@gmail.com
 */
public class ContactClub implements Contact {

	private Club club;
	
	/**
	 * Constructeur
	 * @param clubP
	 */
	public ContactClub(Club clubP) {
		club = clubP;
	}
	
	/* (non-Javadoc)
	 * @see org.jared.android.volley.model.Contact#getMail()
	 */
	@Override
	public String getMail() {
		return club.mail;
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.model.Contact#getMobile()
	 */
	@Override
	public String getMobile() {
		return club.telephone;
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.model.Contact#getTelephone()
	 */
	@Override
	public String getTelephone() {
		return club.telephone;
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.model.Contact#getNom()
	 */
	@Override
	public String getNom() {
		return club.contact;
	}

}
