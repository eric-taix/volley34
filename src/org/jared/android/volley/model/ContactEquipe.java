/**
 * 
 */
package org.jared.android.volley.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Contact d'une Žquipe
 * @author eric.taix@gmail.com
 */
@DatabaseTable(tableName="contact_equipe")
public class ContactEquipe implements Contact {

	@DatabaseField(columnName="id", generatedId=true)
	public int id;
	@DatabaseField(useGetSet=true, columnName="nom")
	private String nom;
	@DatabaseField(useGetSet=true, columnName="mail")
	private String mail;
	@DatabaseField(useGetSet=true, columnName="mobile")
	private String mobile;
	@DatabaseField(useGetSet=true, columnName="telephone")
	private String telephone;

	/*
	 * (non-Javadoc)
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

	/*
	 * (non-Javadoc)
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

	/*
	 * (non-Javadoc)
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

	/*
	 * (non-Javadoc)
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
	
	public boolean isEmpty() {
		return (nom == null || nom.length() == 0);
	}

}
