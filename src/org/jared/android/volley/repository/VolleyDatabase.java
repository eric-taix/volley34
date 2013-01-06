package org.jared.android.volley.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class VolleyDatabase extends SQLiteOpenHelper {

	// Version de la base de données
	private static final int DATABASE_VERSION = 6;
	// Nom de la BD
	private static final String DATABASE_NAME = "volley.db";

	public VolleyDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(MajDAO.TABLE_CREATE);
		database.execSQL(ClubDAO.CLUB_TABLE_CREATE);
		database.execSQL(EquipeDAO.TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(VolleyDatabase.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + MajDAO.TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + ClubDAO.TABLE_CLUB);
		db.execSQL("DROP TABLE IF EXISTS " + EquipeDAO.TABLE);
		onCreate(db);
	}
}
