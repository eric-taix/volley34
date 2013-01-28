/**
 * 
 */
package org.jared.android.volley.ui;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.VolleyApplication;
import org.jared.android.volley.model.Club;
import org.jared.android.volley.model.ContactClub;
import org.jared.android.volley.model.Equipe;
import org.jared.android.volley.model.EquipesClubResponse;
import org.jared.android.volley.model.Event;
import org.jared.android.volley.model.EventsResponse;
import org.jared.android.volley.model.Update;
import org.jared.android.volley.repository.VolleyDatabaseHelper;
import org.jared.android.volley.ui.action.ContactAction;
import org.jared.android.volley.ui.action.MailAction;
import org.jared.android.volley.ui.action.PhoneAction;
import org.jared.android.volley.ui.action.ShareAction;
import org.jared.android.volley.ui.action.SmsAction;
import org.jared.android.volley.ui.adapter.ClubInformationAdapter;
import org.jared.android.volley.ui.adapter.ContactAdapter;
import org.jared.android.volley.ui.adapter.EventAdapter;
import org.jared.android.volley.ui.adapter.SimpleEquipesAdapter;
import org.jared.android.volley.ui.adapter.commons.CollapsableAdapter;
import org.jared.android.volley.ui.adapter.commons.SectionAdapter;
import org.jared.android.volley.ui.widget.quickaction.Action;
import org.jared.android.volley.ui.widget.quickaction.ActionItem;
import org.jared.android.volley.ui.widget.quickaction.QuickAction;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.App;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OrmLiteDao;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Activit� permettant d'afficher un club
 * 
 * @author eric.taix@gmail.com
 */
@SuppressLint("DefaultLocale")
@EActivity(value = R.layout.club_detail_layout)
public class ClubActivity extends SherlockActivity implements OnItemClickListener {

	public static final String EXTRA_CLUB = "CLUB";

	public static final int ID_MAIL = 0;
	public static final int ID_PHONE = 1;
	public static final int ID_SMS = 2;
	public static final int ID_CONTACT = 3;
	public static final int ID_SHARE = 4;

	@OrmLiteDao(helper = VolleyDatabaseHelper.class, model = Update.class)
	Dao<Update, String> updateDao;
	@OrmLiteDao(helper = VolleyDatabaseHelper.class, model = Club.class)
	Dao<Club, String> clubDao;
	@OrmLiteDao(helper = VolleyDatabaseHelper.class, model = Equipe.class)
	Dao<Equipe, String> equipeDao;
	@OrmLiteDao(helper = VolleyDatabaseHelper.class, model = Event.class)
	Dao<Event, String> eventDao;

	@App
	VolleyApplication application;
	@ViewById(R.id.title)
	TextView title;
	@ViewById(R.id.logo)
	ImageView logo;
	@ViewById(R.id.favorite)
	ImageView favorite;
	@ViewById(R.id.listView)
	ListView listView;
	@ViewById(R.id.maj)
	TextView maj;
	@ViewById(R.id.progressBar)
	ProgressBar progressBar;

	// Le club courant
	private Club currentClub;
	// Options de l'ImageLoader pour les logo des clubs: cache m�moire + cache disque
	private static DisplayImageOptions logoOptions = new DisplayImageOptions.Builder().showStubImage(R.drawable.empty).showImageForEmptyUri(R.drawable.empty)
			.cacheInMemory().build();

	private QuickAction quickAction;
	// Adpater qui affiche la liste des �quipes
	private SimpleEquipesAdapter equipeAdapter;
	// Adapter qui contient les autres adapters
	private SectionAdapter sectionAdapter;
	// Adapteur pour les �v�nements
	private EventAdapter eventAdapter;

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ActionBar ab = getSupportActionBar();
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		setTitle("Club");
		// Si l'activit� est lanc�e avec des extras (ce qui devrait �tre le cas syst�matiquement)
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Parcelable clubToShow = extras.getParcelable(EXTRA_CLUB);
			if (clubToShow != null) {
				currentClub = (Club) clubToShow;
			}
		}
	}

	@OptionsItem(android.R.id.home)
	boolean backHome() {
		finish();
		return true;
	}

	@AfterViews
	public void afterViews() {
		sectionAdapter = new SectionAdapter(this, R.layout.list_header);

		ClubInformationAdapter informationAdapter = new ClubInformationAdapter(this, currentClub);
		sectionAdapter.addSection("INFORMATIONS", informationAdapter);

		ContactAdapter contactAdapter = new ContactAdapter(this);
		contactAdapter.addContact(new ContactClub(currentClub));
		CollapsableAdapter collapseContact = new CollapsableAdapter(this, contactAdapter, sectionAdapter);
		sectionAdapter.addSection("CONTACT", collapseContact);

		equipeAdapter = new SimpleEquipesAdapter(this);
		CollapsableAdapter collapseEquipe = new CollapsableAdapter(this, equipeAdapter, sectionAdapter);
		collapseEquipe.setTexts("Toutes les �quipes", "R�duire");
		sectionAdapter.addSection("EQUIPES", collapseEquipe);

		eventAdapter = new EventAdapter(this);
		CollapsableAdapter collapseEvent = new CollapsableAdapter(this, eventAdapter, sectionAdapter);
		collapseEvent.setTexts("Tous les �v�nements", "R�duire");
		sectionAdapter.addSection("CALENDRIER", collapseEvent);

		listView.setCacheColorHint(getResources().getColor(R.color.transparent));
		// On positionne un divider plus "sympa"
		int[] colors = { 0, 0xFF777777, 0 };
		listView.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		listView.setDividerHeight(0);
		listView.setAdapter(sectionAdapter);
		// On fixe les infos
		title.setText(currentClub.nomCourt);
		if (currentClub.urlLogo != null && currentClub.urlLogo.length() > 0) {
			logo.setVisibility(View.VISIBLE);
			// On affiche le logo du club en t�che de fond
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(currentClub.urlLogo, logo, logoOptions);
		}
		else {
			logo.setVisibility(View.GONE);
		}
		favorite.setImageDrawable(currentClub.favorite ? getResources().getDrawable(R.drawable.ic_star_enabled) : getResources().getDrawable(
				R.drawable.ic_star_disabled));
		// On r�pond au click de la liste
		listView.setOnItemClickListener(this);
		// Cr�ation du menu pour le contact
		quickAction = new QuickAction(this);
		contactAdapter.setContactQuickAction(quickAction);
		if (currentClub.mail != null && currentClub.mail.length() > 0) {
			ActionItem mailAction = new ActionItem(ID_MAIL, "Envoyer un email", getResources().getDrawable(R.drawable.ic_mail), new MailAction(
					currentClub.mail, currentClub.nom));
			quickAction.addActionItem(mailAction);
		}
		if (currentClub.telephone != null && currentClub.telephone.length() > 0) {
			ActionItem phoneAction = new ActionItem(ID_PHONE, "T�l�phoner", getResources().getDrawable(R.drawable.ic_phone), new PhoneAction(
					currentClub.telephone));
			ActionItem smsAction = new ActionItem(ID_SMS, "Envoyer un SMS", getResources().getDrawable(R.drawable.ic_sms), new SmsAction(currentClub.telephone));
			quickAction.addActionItem(phoneAction);
			quickAction.addActionItem(smsAction);
		}
		if ((currentClub.telephone != null && currentClub.telephone.length() > 0) || (currentClub.mail != null && currentClub.mail.length() > 0)) {
			ActionItem contactAction = new ActionItem(ID_CONTACT, "Ajouter aux contacts", getResources().getDrawable(R.drawable.ic_address_book),
					new ContactAction(currentClub));
			ActionItem shareAction = new ActionItem(ID_SHARE, "Partager", getResources().getDrawable(R.drawable.ic_share), new ShareAction(currentClub));
			quickAction.addActionItem(contactAction);
			quickAction.addActionItem(shareAction);
		}

		// Mise en place du listener sur le quick action
		quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				ActionItem actionItem = quickAction.getActionItem(pos);
				Action action = actionItem.getAction();
				executeAction(action);
			}
		});
		updateUI();
		// En t�che de fond on interroge le serveur
		progressBar.setVisibility(View.VISIBLE);
		updateFromNetwork();
	}

	/**
	 * Click sur le bouton favorite
	 */
	@Click(R.id.favorite)
	public void favoriteClicked() {
		int id_favorite = currentClub.favorite ? R.drawable.ic_star_disabled : R.drawable.ic_star_enabled;
		favorite.setImageDrawable(getResources().getDrawable(id_favorite));
		currentClub.favorite = !currentClub.favorite;
		updateClub(currentClub);
		String msg = currentClub.favorite ? currentClub.nomCourt + " a �t� ajout� aux favoris" : currentClub.nomCourt + " a �t� supprim� des favoris";
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	@Background
	void updateClub(Club clubToUpdate) {
		try {
			clubDao.update(clubToUpdate);
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while updating club informations into the database");
		}
	}

	/**
	 * Met � jour l'interface graphique en fonction des donn�es contenues dans la BD
	 */
	@UiThread
	void updateUI() {
		Date datetime = VolleyDatabaseHelper.getLastUpdate(updateDao, "EQUIPES-CLUB-" + currentClub.code);
		if (datetime != null) {
			maj.setText(DateUtils.getRelativeTimeSpanString(datetime.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS,
					DateUtils.FORMAT_NUMERIC_DATE));
		}
		else {
			maj.setText("");
		}
		
		// Update the teams list for the current club (from the Database)
		try {
			List<Equipe> equipes = equipeDao.queryForEq("code_club", currentClub.code);
			if (equipes != null) {
				equipeAdapter.setEquipes(equipes);
				sectionAdapter.notifyDataSetChanged();
			}
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while retrieving teams list for club "+currentClub.code);
		}
		
		// Update calendars (from the DB)
		try {
			List<Event> events = eventDao.queryForEq("code", "CLUB-"+currentClub.code);
			eventAdapter.setEvents(events);
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while retrieving events list for club "+currentClub.code);
		}
		
		progressBar.setVisibility(View.GONE);
	}

	/**
	 * Update informations from the network and save datas to DB
	 */
	@Background
	public void updateFromNetwork() {
		try {
			EquipesClubResponse ecl = application.restClient.getEquipes(currentClub.code);
			List<Equipe> newEquipes = ecl.equipes;
			// Update the favorites flag (which does not exist in the REST response)
			List<Equipe> oldEquipes = equipeDao.queryForEq("code_club", currentClub.code);
			for (Equipe newEquipe : newEquipes) {
				int index = oldEquipes.indexOf(newEquipe);
				if (index != -1) {
					Equipe oldEquipe = oldEquipes.get(index);
					newEquipe.favorite = oldEquipe.favorite;
				}
			}
			// Remove all all teams
			equipeDao.delete(oldEquipes);
			// Save all new teams
			for (Equipe newEquipe : newEquipes) {
				equipeDao.create(newEquipe);
			}
			
			// Update calendars
			updateCalendarFromNetwork(currentClub.code);
			
			// Set the last update information
			VolleyDatabaseHelper.updateLastUpdate(updateDao, "EQUIPES-CLUB-" + currentClub.code);
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while updating last update of teams for club " + currentClub, e);
		}
		finally {
			updateUI();
		}
	}

	/**
	 * Update calendars from network and save data to DB
	 */
	public void updateCalendarFromNetwork(String codeClub) {
		try {
			EventsResponse er = application.restClient.getClubCalendar(codeClub);
			// First delete all events which are connected to this club
			List<Event> oldEvents = eventDao.queryForEq("code","CLUB-"+codeClub);
			eventDao.delete(oldEvents);
			// The insert new datas
			List<Event> events = er.events;
			for (Event event : events) {
				event.code = "CLUB-"+codeClub;
				CreateOrUpdateStatus status = eventDao.createOrUpdate(event);
				Log.d("Volley34","Nb of changed lines: "+status.getNumLinesChanged());
			}
		}
		catch (Exception e) {
			Log.e("Volley34","Error while retrieving (from network) events list for club "+codeClub);
		}
	}

	@Background
	public void executeAction(Action action) {
		if (action != null) {
			action.execute(ClubActivity.this);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Object obj = parent.getAdapter().getItem(position);
		// On affiche le d�tail d'une �quipe
		if (obj instanceof Equipe) {
			EquipeActivity_.startActivityForResult(this, ((Equipe) obj).codeEquipe, 0);
		}
	}

}
