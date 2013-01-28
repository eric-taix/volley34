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
}
