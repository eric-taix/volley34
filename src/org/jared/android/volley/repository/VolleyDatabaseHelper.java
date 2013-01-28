package org.jared.android.volley.repository;

import java.sql.SQLException;
import java.util.Date;

import org.jared.android.volley.model.Club;
import org.jared.android.volley.model.ContactEquipe;
import org.jared.android.volley.model.Equipe;
import org.jared.android.volley.model.EquipeDetail;
import org.jared.android.volley.model.Event;
import org.jared.android.volley.model.Gymnase;
import org.jared.android.volley.model.Update;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class VolleyDatabaseHelper extends OrmLiteSqliteOpenHelper {

	// Database version: must be changed if at least one date is changed
	private static final int DATABASE_VERSION = 15;
	// Database name
	private static final String DATABASE_NAME = "volley.db";

	public VolleyDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(VolleyDatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Update.class);
			TableUtils.createTable(connectionSource, Club.class);
			TableUtils.createTable(connectionSource, Equipe.class);
			TableUtils.createTable(connectionSource, EquipeDetail.class);
			TableUtils.createTable(connectionSource, ContactEquipe.class);
			TableUtils.createTable(connectionSource, Gymnase.class);
			TableUtils.createTable(connectionSource, Event.class);
		}
		catch (SQLException e) {
			Log.e(VolleyDatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust the various data to match the new version
	 * number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(VolleyDatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Update.class, true);
			TableUtils.dropTable(connectionSource, Club.class, true);
			TableUtils.dropTable(connectionSource, Equipe.class, true);
			TableUtils.dropTable(connectionSource, EquipeDetail.class, true);
			TableUtils.dropTable(connectionSource, ContactEquipe.class, true);
			TableUtils.dropTable(connectionSource, Gymnase.class, true);
			TableUtils.dropTable(connectionSource, Event.class, true);

			onCreate(db, connectionSource);
		}
		catch (SQLException e) {
			Log.e(VolleyDatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get the last updates information we've got from the code paramter
	 * @param code
	 * @return
	 */
	public static Date getLastUpdate(Dao<Update, String> updateDao, String code) {
		try {
			Update update = updateDao.queryForId(code);
			return (update != null && update.dateTime != null ? update.dateTime : null);
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while retreiving last updates for code "+code);
			return null;
		}
	}
	
	/**
	 * Update the last updates date and time to the current system millis
	 * @param code
	 */
	public static void updateLastUpdate(Dao<Update, String> updateDao, String code) {
		try {
			Update update = updateDao.queryForId(code);
			if (update == null) {
				update = new Update();
				update.code = code;
			}
			update.dateTime = new Date();
			updateDao.createOrUpdate(update);
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while updating last updates for code "+code);
		}
	}

}
