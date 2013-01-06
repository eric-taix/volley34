/**
 * 
 */
package org.jared.android.volley.repository;

import java.util.ArrayList;
import java.util.List;

import org.jared.android.volley.model.Equipe;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe d'accès à la BD pour une équipe
 * 
 * @author eric.taix@gmail.com
 */
public class EquipeDAO {
	public static final String TABLE = "equipe";
	public static final String NOM_CLUB_COURT = "nom_club_court";
	public static final String NOM_CLUB = "nom_club";
	public static final String CODE_CLUB = "code_club";
	public static final String NOM_EQUIPE = "nom_equipe";
	public static final String CODE_EQUIPE = "code_equipe";
	public static final String FAVORITE = "favori";

	public static final String TABLE_CREATE = "create table " + TABLE + "(" + CODE_EQUIPE + " text primary key, " + NOM_EQUIPE + " text, " + NOM_CLUB_COURT
			+ " text, " + NOM_CLUB + " text, " + CODE_CLUB + " text, " + FAVORITE + " boolean);";

	/**
	 * Sauvegarde
	 */
	public static void saveEquipe(SQLiteOpenHelper dbHelper, Equipe equipe) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			db.insert(TABLE, null, getValues(equipe));
		}
		finally {
			if (db != null) {
				db.close();
			}
		}

	}

	/**
	 * Met à jour
	 */
	public static void updateEquipe(SQLiteOpenHelper dbHelper, Equipe equipe) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			db.update(TABLE, getValues(equipe), CODE_EQUIPE + " = ?", new String[] { equipe.codeEquipe });
		}
		finally {
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * Sauvegarde toutes les équipes passées en paramètre d'une équipe particulier. Si le code est null alors cela signifie qu'il s'agit de toutes les équipes,
	 * tous club confondus
	 * 
	 * @param clubs
	 */
	public static void saveAll(SQLiteOpenHelper dbHelper, List<Equipe> equipes, String code) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			// On lit les équipes courantes
			List<Equipe> oldEquipes = getAllEquipes(db, code);
			for (Equipe equipe : equipes) {
				int index = oldEquipes.indexOf(equipe);
				if (index != -1) {
					Equipe oldEquipe = oldEquipes.get(index);
					equipe.favorite = oldEquipe.favorite;
				}
			}
			// On détruit tout en fonction du club
			if (code != null) {
				db.delete(TABLE, CODE_CLUB + " = ?", new String[] { code });
			}
			else {
				db.delete(TABLE, null, null);
			}
			// Et on sauve chacun des clubs
			for (Equipe club : equipes) {
				db.insert(TABLE, null, getValues(club));
			}
		}
		finally {
			if (db != null) {
				db.close();
			}
		}

	}

	/**
	 * Retourne tous
	 */
	public static List<Equipe> getAllEquipes(SQLiteOpenHelper dbHelper, String code) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getReadableDatabase();
			return getAllEquipes(db, code);
		}
		finally {
			if (db != null) {
				db.close();
			}
		}
	}

	private static List<Equipe> getAllEquipes(SQLiteDatabase db, String code) {
		Cursor cursor = null;
		if (code != null) {
			String selectQuery = "SELECT * FROM " + TABLE + " WHERE " + CODE_CLUB + "= ? ORDER BY " + NOM_EQUIPE;
			cursor = db.rawQuery(selectQuery, new String[] { code });
		}
		else {
			String selectQuery = "SELECT * FROM " + TABLE + " ORDER BY " + NOM_EQUIPE;
			cursor = db.rawQuery(selectQuery, null);
		}
		List<Equipe> equipeList = new ArrayList<Equipe>();
		if (cursor.moveToFirst()) {
			do {
				equipeList.add(getEquipe(cursor));
			}
			while (cursor.moveToNext());
		}
		return equipeList;
	}

	/**
	 * Tranforme un résultat de requête en Equipe
	 * 
	 * @param cursor
	 * @return
	 */
	private static Equipe getEquipe(Cursor cursor) {
		Equipe equipe = new Equipe();
		equipe.codeEquipe = cursor.getString(0);
		equipe.nomEquipe = cursor.getString(1);
		equipe.nomClubCourt = cursor.getString(2);
		equipe.nomClub = cursor.getString(3);
		equipe.codeClub = cursor.getString(4);
		equipe.favorite = cursor.getInt(5) > 0;
		return equipe;
	}

	/**
	 * Transforme une équipe ContentValues
	 * 
	 * @param equipe
	 * @return
	 */
	private static ContentValues getValues(Equipe equipe) {
		ContentValues values = new ContentValues();
		values.put(CODE_EQUIPE, equipe.codeEquipe);
		values.put(NOM_EQUIPE, equipe.nomEquipe);
		values.put(NOM_CLUB_COURT, equipe.nomClubCourt);
		values.put(NOM_CLUB, equipe.nomClub);
		values.put(CODE_CLUB, equipe.codeClub);
		values.put(FAVORITE, equipe.favorite);
		return values;
	}
}
