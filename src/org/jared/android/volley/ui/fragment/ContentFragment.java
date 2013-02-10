/**
 * 
 */
package org.jared.android.volley.ui.fragment;

import org.jared.android.volley.R;
import org.jared.android.volley.VolleyApplication;
import org.jared.android.volley.http.RestClient;
import org.jared.android.volley.model.Update;
import org.jared.android.volley.repository.VolleyDatabaseHelper;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
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
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

/**
 * A Fragment which displays a ListView. This fragment delegates some operations (like retreiving the adapter to use, retreiving data from DB or network, ...)
 * to a ContentProvider
 * 
 * @author eric.taix@gmail.com
 */
@EFragment(R.layout.list_layout)
public abstract class ContentFragment extends Fragment implements OnItemClickListener {

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
	private VolleyDatabaseHelper databaseHelper = null;

	@OrmLiteDao(helper = VolleyDatabaseHelper.class, model = Update.class)
	Dao<Update, String> updateDao;

	@AfterViews
	public void afterViews() {
		title.setText(getTitle());

		// Set a better L&F for the divider
		listView.setCacheColorHint(getResources().getColor(R.color.transparent));
		int[] colors = { 0, 0xFF777777, 0 };
		listView.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		listView.setDividerHeight(0);
		listView.setAdapter(getListAdapter());
		listView.setOnItemClickListener(this);
		progressBar.setVisibility(View.VISIBLE);
		updateUI();
		// In the background, request the network
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
	 * Update datas : request the server
	 */
	@Background
	void updateFromNetWork() {
		try {
			Object result = doGetFromNetwork(application.restClient);
			doSaveToDatabase(result);
			VolleyDatabaseHelper.updateLastUpdate(updateDao, getCode());
			updateUIAsync();
		}
		catch (Exception e) {
			Log.e("Volley34", "Error while updating values from network", e);
		}
	}

	/**
	 * Update the UI in a thread
	 * 
	 * @param clubs
	 */
	@UiThread
	void updateUIAsync() {
		updateUI();
	}

	/**
	 * Update the UI: datas are retreived from the DB
	 * 
	 * @param clubs
	 */
	void updateUI() {
		progressBar.setVisibility(View.GONE);
		maj.setText(VolleyDatabaseHelper.getLastUpdate(updateDao, getCode()));
		// Udpate concrete ui
		doUpdateUI();

	}

	/**
	 * Retrieve the DatabaseHelper and create it if needed (can not be injected by AA)
	 */
	protected VolleyDatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this.getActivity(), VolleyDatabaseHelper.class);
		}
		return databaseHelper;
	}

	/**
	 * Returns the adapter for the ListView
	 * @return
	 */
	public abstract ListAdapter getListAdapter();

	/**
	 * Returns a unique code for the update DB table
	 * @return
	 */
	public abstract String getCode();

	/**
	 * Returns the title
	 * @return
	 */
	public abstract String getTitle();

	/**
	 * Update the UI according to the data stored in the DataBase
	 * @param db
	 */
	public abstract void doUpdateUI();

	/**
	 * Retrieve datas from the server
	 * @param client
	 * @return
	 */
	public abstract Object doGetFromNetwork(RestClient client);

	/**
	 * Save data to the Database
	 * @param oject
	 * @param db
	 */
	public abstract void doSaveToDatabase(Object oject);
}
