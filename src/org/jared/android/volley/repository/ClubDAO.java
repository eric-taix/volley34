/**
 * 
 */
package org.jared.android.volley.repository;

import java.util.ArrayList;
import java.util.List;

import org.jared.android.volley.model.Club;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe d'accès à la BD pour un club
 * 
 * @author eric.taix@gmail.com
 */
public class ClubDAO {
	// Définition de la table Club
	public static final String TABLE_CLUB = "club";
	public static final String CLUB_CODE = "code";
	public static final String CLUB_NBEQUIPES = "nb_equipes";
	public static final String CLUB_URLLOGO = "url_logo";
	public static final String CLUB_MAIL = "mail";
	public static final String CLUB_URLSITEWEB = "url_site_web";
	public static final String CLUB_TELEPHONE = "telephone";
	public static final String CLUB_CONTACT = "contact";
	public static final String CLUB_NOM = "nom";
	public static final String CLUB_NOMCOURT = "nom_court";
	public static final String CLUB_FAVORITE = "favori";

	public static final String CLUB_TABLE_CREATE = "create table " + TABLE_CLUB + "(" + CLUB_CODE + " text primary key, " + CLUB_NBEQUIPES + " int, "
			+ CLUB_URLLOGO + " text, " + CLUB_MAIL + " text, " + CLUB_URLSITEWEB + " text, " + CLUB_TELEPHONE + " text, " + CLUB_CONTACT + " text, " + CLUB_NOM
			+ " text, " + CLUB_NOMCOURT + " text, " + CLUB_FAVORITE + " boolean);";

	/**
	 * Ajout un club
	 * 
	 * @param club
	 */
	public static void saveClub(SQLiteOpenHelper dbHelper, Club club) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			db.insert(TABLE_CLUB, null, getValues(club));
		}
		finally {
			if (db != null) {
				db.close();
			}
		}

	}

	/**
	 * Met à jour un club
	 * 
	 * @param club
	 */
	public static void updateClub(SQLiteOpenHelper dbHelper, Club club) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			db.update(TABLE_CLUB, getValues(club), CLUB_CODE + " = ?", new String[] { club.code });
		}
		finally {
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * Sauvegarde tous les clubs
	 * 
	 * @param clubs
	 */
	public static void saveAll(SQLiteOpenHelper dbHelper, List<Club> clubs) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			// On lit les clubs courants
			List<Club> oldClubs = getAllClubs(db);
			for (Club club : clubs) {
				int index = oldClubs.indexOf(club);
				if (index != -1) {
					Club oldClub = oldClubs.get(index);
					club.favorite = oldClub.favorite;
				}
			}
			// On détruit tout
			db.delete(TABLE_CLUB, null, null);
			// Et on sauve chacun des clubs
			for (Club club : clubs) {
				db.insert(TABLE_CLUB, null, getValues(club));
			}
		}
		finally {
			if (db != null) {
				db.close();
			}
		}

	}

	/**
	 * Retourne tous les clubs
	 * 
	 * @param dbHelper
	 * @return
	 */
	public static List<Club> getAllClubs(SQLiteOpenHelper dbHelper) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getReadableDatabase();
			return getAllClubs(db);
		}
		finally {
			if (db != null) {
				db.close();
			}
		}
	}

	private static List<Club> getAllClubs(SQLiteDatabase db) {
		String selectQuery = "SELECT * FROM " + TABLE_CLUB + " ORDER BY "+CLUB_NOMCOURT;
		Cursor cursor = db.rawQuery(selectQuery, null);
		List<Club> clubList = new ArrayList<Club>();
		if (cursor.moveToFirst()) {
			do {
				clubList.add(getClub(cursor));
			}
			while (cursor.moveToNext());
		}
		return clubList;
	}
	
	/**
	 * Tranforme un résultat de requête en Club
	 * 
	 * @param cursor
	 * @return
	 */
	private static Club getClub(Cursor cursor) {
		Club club = new Club();
		club.code = cursor.getString(0);
		club.nbEquipes = cursor.getInt(1);
		club.urlLogo = cursor.getString(2);
		club.mail = cursor.getString(3);
		club.urlSiteWeb = cursor.getString(4);
		club.telephone = cursor.getString(5);
		club.contact = cursor.getString(6);
		club.nom = cursor.getString(7);
		club.nomCourt = cursor.getString(8);
		club.favorite = cursor.getInt(9) > 0;
		return club;
	}

	/**
	 * Transforme un club en ContentValues
	 * 
	 * @param club
	 * @return
	 */
	private static ContentValues getValues(Club club) {
		ContentValues values = new ContentValues();
		values.put(CLUB_CODE, club.code);
		values.put(CLUB_NBEQUIPES, club.nbEquipes);
		values.put(CLUB_URLLOGO, club.urlLogo);
		values.put(CLUB_MAIL, club.mail);
		values.put(CLUB_URLSITEWEB, club.urlSiteWeb);
		values.put(CLUB_TELEPHONE, club.telephone);
		values.put(CLUB_CONTACT, club.contact);
		values.put(CLUB_NOM, club.nom);
		values.put(CLUB_NOMCOURT, club.nomCourt);
		values.put(CLUB_FAVORITE, club.favorite);
		return values;
	}
}
