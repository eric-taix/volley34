/**
 * 
 */
package org.jared.android.volley.model;

import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * D�finition d'une �quipe
 * @author eric.taix@gmail.com
 */
@Root(name="Row",strict=false)
public class EquipeDetail implements Parcelable {
	
	public String codeEquipe;
	public ContactEquipe contactRespChampionnat;
	public ContactEquipe contactSupplChampionnat;
	public ContactEquipe contactRespCoupe;
	public ContactEquipe contactSupplCoupe;

	public Gymnase gymnaseChampionnat;
	public Gymnase gymnaseCoupe;

//			
//			GPSGymnaseCoupe="" 
//			TelGymnaseCoupe="" 
//			VilleGymnaseCoupe="" 
//			QuartierGymnaseCoupe="" 
	
//			CPGymnaseCoupe="" 
//			AdresseGymnaseCoupe="" 
//			NomCompletGymnaseCoupe="" 
//			NomGymnaseCoupe="" 
//			
//			HeureCoupe="" 
//			JourSemaineCoupe="" 
//			NomEquipeCoupe="" 
//			
//			---------------
//			

//			
//			GPSGymnase="43.669099|3.717802" 
//			TelGymnase="" 
//			VilleGymnase="Vailhauqu�s" 
//			QuartierGymnase="" 
//			CPGymnase="34570" 
//			AdresseGymnase="" 
//			NomCompletGymnase="Salle Polyvalente (Vailhauqu�s)" 
//			NomGymnase="Salle Polyvalente" 
//			Heure="01/09/2011 20:30:00" 
//			JourSemaine="Mardi" 
//			
//			NomClubCourt="FR Vailhauques" 
//			URLLogoClub="" 
//			SigleClub="" 
//			MailClub="delphine.coutrot@gmail.com " 
//			URLClub="http://vailhauquesvb.free.fr/" 
//			TelephoneClub="" 
//			ContactClub="Delphine COUTROT" 
//			NomClub="Foyer Rural Vailhauques" 
//			CodeClub="VAILHAUQUES" 
//			Nom="Les Aspics (Vailhauques)" 
//			
//			NomEquipe="Les Aspics (Vailhauques)" 
//			
//			EquipeCode="VAILHAUQUES2"/>
	

	/**
	 * Constructeur par d�fait
	 */
	public EquipeDetail() {
	}
	
	/**
	 * Constructeur utilis� qd un cr�e un club depuis un Parcelable
	 * @param in
	 */
	public EquipeDetail(Parcel in) {
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
		dest.writeValue(contactRespChampionnat);
		dest.writeValue(contactSupplChampionnat);
		dest.writeValue(contactRespCoupe);
		dest.writeValue(contactSupplCoupe);
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
		contactRespChampionnat=(ContactEquipe)in.readValue(cl);
		contactSupplChampionnat=(ContactEquipe)in.readValue(cl);
		contactRespCoupe=(ContactEquipe)in.readValue(cl);
		contactSupplCoupe=(ContactEquipe)in.readValue(cl);
		
	}

	/**
	 * M�thode de cr�ation d'un club depuis un Parcelable appel�e depuis le syst�me
	 */
	public static final Parcelable.Creator<EquipeDetail> CREATOR = new Parcelable.Creator<EquipeDetail>() {
		public EquipeDetail createFromParcel(Parcel in) {
			return new EquipeDetail(in);
		}

		public EquipeDetail[] newArray(int size) {
			return new EquipeDetail[size];
		}
	};
}
