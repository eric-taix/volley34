/**
 * 
 */
package org.jared.android.volley.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Définition d'un club
 * @author eric.taix@gmail.com
 */
@Root(name="Row",strict=false)
public class Club implements Parcelable {
	
	@Attribute(name="CodeClub")
	public String code;
	@Attribute(name="NbEquipes")
	public int nbEquipes;
	@Attribute(name="URLLogoClub")
	public String urlLogo;
	@Attribute(name="MailClub")
	public String mail;
	@Attribute(name="URLClub")
	public String urlSiteWeb;
	@Attribute(name="TelephoneClub")
	public String telephone;
	@Attribute(name="ContactClub")
	public String contact;
	@Attribute(name="NomClub")
	public String nom;
	@Attribute(name="NomClubCourt")
	public String nomCourt;
	
	public boolean favorite = false;
	

	/**
	 * Constructeur par défait
	 */
	public Club() {
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		Club other = (Club) obj;
		if (code == null) {
			if (other.code != null) return false;
		}
		else if (!code.equals(other.code)) return false;
		return true;
	}

	/**
	 * Constructeur utilisé qd un crée un club depuis un Parcelable
	 * @param in
	 */
	public Club(Parcel in) {
		readFromParcel(in);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(code);
		dest.writeValue(nbEquipes);
		dest.writeValue(urlLogo);
		dest.writeValue(mail);
		dest.writeValue(urlSiteWeb);
		dest.writeValue(telephone);
		dest.writeValue(contact);
		dest.writeValue(nom);
		dest.writeValue(nomCourt);
		dest.writeValue(favorite);
	}

	/**
	 * 
	 * Called from the constructor to create this object from a parcel.
	 * 
	 * @param in
	 *            parcel from which to re-create object
	 */
	private void readFromParcel(Parcel in) {
		ClassLoader cl = this.getClass().getClassLoader();
		code = (String) in.readValue(cl);
		nbEquipes = (Integer) in.readValue(cl);
		urlLogo = (String) in.readValue(cl);
		mail = (String) in.readValue(cl);
		urlSiteWeb = (String) in.readValue(cl);
		telephone = (String) in.readValue(cl);
		contact = (String) in.readValue(cl);
		nom = (String) in.readValue(cl);
		nomCourt = (String) in.readValue(cl);
		favorite = (Boolean) in.readValue(cl);
	}

	/**
	 * Méthode de création d'un club depuis un Parcelable appelée depuis le système
	 */
	public static final Parcelable.Creator<Club> CREATOR = new Parcelable.Creator<Club>() {
		public Club createFromParcel(Parcel in) {
			return new Club(in);
		}

		public Club[] newArray(int size) {
			return new Club[size];
		}
	};
}
