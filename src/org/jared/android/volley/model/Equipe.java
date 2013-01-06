/**
 * 
 */
package org.jared.android.volley.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Définition d'une équipe
 * @author eric.taix@gmail.com
 */
@Root(name="Row",strict=false)
public class Equipe implements Parcelable {
	
	@Attribute(name="NomClubCourt")
	public String nomClubCourt;
	@Attribute(name="NomClub")
	public String nomClub;
	@Attribute(name="CodeClub")
	public String codeClub;
	@Attribute(name="NomEquipe")
	public String nomEquipe;
	@Attribute(name="EquipeCode")
	public String codeEquipe;
	
	public boolean favorite = false;
	

	/**
	 * Constructeur par défait
	 */
	public Equipe() {
	}
	
	/**
	 * Constructeur utilisé qd un crée un club depuis un Parcelable
	 * @param in
	 */
	public Equipe(Parcel in) {
		readFromParcel(in);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeEquipe == null) ? 0 : codeEquipe.hashCode());
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
		Equipe other = (Equipe) obj;
		if (codeEquipe == null) {
			if (other.codeEquipe != null) return false;
		}
		else if (!codeEquipe.equals(other.codeEquipe)) return false;
		return true;
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
		dest.writeValue(nomClubCourt);
		dest.writeValue(nomClub);
		dest.writeValue(codeClub);
		dest.writeValue(nomEquipe);
		dest.writeValue(codeEquipe);
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
		nomClubCourt=(String)in.readValue(cl);
		nomClub=(String)in.readValue(cl);
		codeClub=(String)in.readValue(cl);
		nomEquipe=(String)in.readValue(cl);
		codeEquipe=(String)in.readValue(cl);
		favorite = (Boolean) in.readValue(cl);
	}

	/**
	 * Méthode de création d'un club depuis un Parcelable appelée depuis le système
	 */
	public static final Parcelable.Creator<Equipe> CREATOR = new Parcelable.Creator<Equipe>() {
		public Equipe createFromParcel(Parcel in) {
			return new Equipe(in);
		}

		public Equipe[] newArray(int size) {
			return new Equipe[size];
		}
	};
}
