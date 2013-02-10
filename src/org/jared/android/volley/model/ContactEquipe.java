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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ContactEquipe other = (ContactEquipe) obj;
		if (mail == null) {
			if (other.mail != null) return false;
		}
		else if (!mail.equals(other.mail)) return false;
		if (mobile == null) {
			if (other.mobile != null) return false;
		}
		else if (!mobile.equals(other.mobile)) return false;
		if (nom == null) {
			if (other.nom != null) return false;
		}
		else if (!nom.equals(other.nom)) return false;
		if (telephone == null) {
			if (other.telephone != null) return false;
		}
		else if (!telephone.equals(other.telephone)) return false;
		return true;
	}

}
