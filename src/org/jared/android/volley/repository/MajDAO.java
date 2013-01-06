/**
 * 
 */
package org.jared.android.volley.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Classe d'acc�s � la BD pour un club
 * 
 * @author eric.taix@gmail.com
 */
public class MajDAO {
	// D�finition de la table Club
	public static final String TABLE = "maj";
	public static final String CODE = "code";
	public static final String DATE = "date_time";

	public static final String TABLE_CREATE = "create table " + TABLE + "(" + CODE + " text primary key, " + DATE + " text);";
	@SuppressLint("SimpleDateFormat")
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Met � jour la date de mise � jour d'un code
	 * 
	 * @param club
	 */
	@SuppressLint("SimpleDateFormat")
	public static void udateMaj(SQLiteOpenHelper dbHelper, String code) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			// On supprime d'abord la donn�e de maj
			db.delete(TABLE, CODE + " = ?", new String[] {code});
			Date date = new Date();
			ContentValues initialValues = new ContentValues(); 
			initialValues.put(CODE, code);
			initialValues.put(DATE, DATE_FORMAT.format(date));
			db.insert(TABLE, null, initialValues);
		}
		finally {
			if (db != null) {
				db.close();
			}
		}

	}

	/**
	 * Met � jour un club
	 * 
	 * @param club
	 */
	public static Date getMaj(SQLiteOpenHelper dbHelper, String code) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE "+CODE+" = ?", new String[] {code});
			if (cursor.moveToFirst()) {
				String dateStr = cursor.getString(1);
				try {
					Date date = DATE_FORMAT.parse(dateStr);
					return date;
				}
				catch (ParseException e) {
					Log.e("VOLLEY-34", "Erreur de du parsing de la date de maj du code "+code, e);
				}
			}			
		}
		finally {
			if (db != null) {
				db.close();
			}
		}
		return null;
	}
}
