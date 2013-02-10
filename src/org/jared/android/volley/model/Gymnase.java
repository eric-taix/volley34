/**
 * 
 */
package org.jared.android.volley.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Données d'un gymnase
 * @author eric.taix@gmail.com
 */
@DatabaseTable(tableName="gymnase")
public class Gymnase {
	@DatabaseField(columnName="id", generatedId=true)
	public int id;
	@DatabaseField(columnName="gps")
	public String gps;
	@DatabaseField(columnName="telephone")
	public String telephone;
	@DatabaseField(columnName="nom")
	public String nom;
	@DatabaseField(columnName="nomComplet")
	public String nomComplet;
	@DatabaseField(columnName="quartier")
	public String quartier;
	@DatabaseField(columnName="adresse")
	public String adresse;
	@DatabaseField(columnName="cp")
	public String cp;
	@DatabaseField(columnName="ville")
	public String ville;
	@DatabaseField(columnName="heure")
	public String heure;
	@DatabaseField(columnName="jour")
	public String jour;
	
	public boolean isEmpty() {
		return (nom == null || nom.trim().length() == 0);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result + ((cp == null) ? 0 : cp.hashCode());
		result = prime * result + ((gps == null) ? 0 : gps.hashCode());
		result = prime * result + ((heure == null) ? 0 : heure.hashCode());
		result = prime * result + ((jour == null) ? 0 : jour.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((nomComplet == null) ? 0 : nomComplet.hashCode());
		result = prime * result + ((quartier == null) ? 0 : quartier.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((ville == null) ? 0 : ville.hashCode());
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
		Gymnase other = (Gymnase) obj;
		if (adresse == null) {
			if (other.adresse != null) return false;
		}
		else if (!adresse.equals(other.adresse)) return false;
		if (cp == null) {
			if (other.cp != null) return false;
		}
		else if (!cp.equals(other.cp)) return false;
		if (gps == null) {
			if (other.gps != null) return false;
		}
		else if (!gps.equals(other.gps)) return false;
		if (heure == null) {
			if (other.heure != null) return false;
		}
		else if (!heure.equals(other.heure)) return false;
		if (jour == null) {
			if (other.jour != null) return false;
		}
		else if (!jour.equals(other.jour)) return false;
		if (nom == null) {
			if (other.nom != null) return false;
		}
		else if (!nom.equals(other.nom)) return false;
		if (nomComplet == null) {
			if (other.nomComplet != null) return false;
		}
		else if (!nomComplet.equals(other.nomComplet)) return false;
		if (quartier == null) {
			if (other.quartier != null) return false;
		}
		else if (!quartier.equals(other.quartier)) return false;
		if (telephone == null) {
			if (other.telephone != null) return false;
		}
		else if (!telephone.equals(other.telephone)) return false;
		if (ville == null) {
			if (other.ville != null) return false;
		}
		else if (!ville.equals(other.ville)) return false;
		return true;
	}
	

}
