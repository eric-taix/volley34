/**
 * 
 */
package org.jared.android.volley.ui.fragment;

import java.sql.SQLException;
import java.util.Date;

import org.jared.android.volley.R;
import org.jared.android.volley.VolleyApplication;
import org.jared.android.volley.model.Update;
import org.jared.android.volley.repository.VolleyDatabaseHelper;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.App;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.OrmLiteDao;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.j256.ormlite.dao.Dao;

/**
 * Fragment permettant d'afficher le fait qu'une fonctionnalité n'existe pas
 * 
 * @author eric.taix@gmail.com
 */
@EFragment(R.layout.list_layout)
public class ContentFragment extends Fragment {

	@ViewById(R.id.list)
	ListView listView;
	@ViewById(R.id.maj)
	TextView maj;
	@ViewById(R.id.progressBar)
	ProgressBar progressBar;
	@ViewById(R.id.title)
	TextView title;
	@App
	VolleyApplication application;
	private ContentFragmentProvider provider;

	@OrmLiteDao(helper = VolleyDatabaseHelper.class, model = Update.class)
	Dao<Update, String> updateDao;

	@AfterViews
	public void afterViews() {
		this.provider.init(this);
		title.setText(provider.getTitle());

		// On positionne un divider plus "sympa"
		listView.setCacheColorHint(getResources().getColor(R.color.transparent));
		int[] colors = { 0, 0xFF777777, 0 };
		listView.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		listView.setDividerHeight(0);
		listView.setAdapter(provider.getListAdapter());
		listView.setOnItemClickListener(provider);
		// On met à jour l'interface
		progressBar.setVisibility(View.VISIBLE);
		updateUI();
		// En tâche de fond on interroge le serveur
		progressBar.setVisibility(View.VISIBLE);
		updateFromNetWork();
	}

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		updateUI();
	}

	/**
	 * Constructeur
	 * 
	 * @param provider
	 */
	public void setProvider(ContentFragmentProvider provider) {
		this.provider = provider;
	}

	/**
	 * Met à jour les données en interrogeant le serveur
	 */
	@Background
	void updateFromNetWork() {
		try {
			Object result = provider.doGetFromNetwork(application.restClient);
			provider.doSaveToDatabase(result);
			Update update = updateDao.queryForId(provider.getCode());
			if (update == null) {
				update = new Update();
			}
			update.dateTime = new Date();
			updateDao.createOrUpdate(update);
			updateUI();
		}
		catch (Exception e) {
			Log.e("Volley34","Error while updating values from network",e);
		}
		finally {
			updateUI();	
		}
	}

	/**
	 * Update the UI: datas are retreived from the DB
	 * 
	 * @param clubs
	 */
	@UiThread
	void updateUI() {
		Update update;
		try {
			update = updateDao.queryForId(provider.getCode());
			if (update != null && update.dateTime != null) {
				maj.setText(DateUtils.getRelativeTimeSpanString(update.dateTime.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS,
						DateUtils.FORMAT_NUMERIC_DATE));
			}
			else {
				maj.setText("");
			}
			// Udpate concrete ui
			provider.doUpdateUI();
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while updating last update informations from DB", e);
			maj.setText("");
		}
		finally {
			progressBar.setVisibility(View.GONE);
		}
	}
}
