/**
 * 
 */
package org.jared.android.volley.model;

import org.simpleframework.xml.Root;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Définition d'une équipe
 * @author eric.taix@gmail.com
 */
@Root(name="Row",strict=false)
@DatabaseTable(tableName="equipe_detail")
public class EquipeDetail implements Parcelable {
	
	@DatabaseField(id=true, columnName="code_equipe")
	public String codeEquipe;
	@DatabaseField(foreign=true, foreignAutoRefresh=true, foreignAutoCreate=true, columnName="contact_resp_champ")
	public ContactEquipe contactRespChampionnat;
	@DatabaseField(foreign=true, foreignAutoRefresh=true, foreignAutoCreate=true, columnName="contact_suppl_champ")
	public ContactEquipe contactSupplChampionnat;
	@DatabaseField(foreign=true, foreignAutoRefresh=true, foreignAutoCreate=true, columnName="contact_resp_coupe")
	public ContactEquipe contactRespCoupe;
	@DatabaseField(foreign=true, foreignAutoRefresh=true, foreignAutoCreate=true, columnName="contact_suppl_coupe")
	public ContactEquipe contactSupplCoupe;
	@DatabaseField(foreign=true, foreignAutoRefresh=true, foreignAutoCreate=true, columnName="gymnase_champ")
	public Gymnase gymnaseChampionnat;
	@DatabaseField(foreign=true, foreignAutoRefresh=true, foreignAutoCreate=true, columnName="gymnase-coupe")
	public Gymnase gymnaseCoupe;

	/**
	 * Constructeur par défait
	 */
	public EquipeDetail() {
	}
	
	/**
	 * Constructeur utilisé qd un crée un club depuis un Parcelable
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
	 * Méthode de création d'un club depuis un Parcelable appelée depuis le système
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
