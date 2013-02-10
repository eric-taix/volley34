/**
 * 
 */
package org.jared.android.volley.ui.fragment;

import java.sql.SQLException;
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
import org.jared.android.volley.ui.MenuActivity;
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
import org.jared.android.volley.ui.adapter.commons.Section;
import org.jared.android.volley.ui.adapter.commons.SectionAdapter;
import org.jared.android.volley.ui.widget.quickaction.Action;
import org.jared.android.volley.ui.widget.quickaction.ActionItem;
import org.jared.android.volley.ui.widget.quickaction.QuickAction;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.App;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.OrmLiteDao;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Activité permettant d'afficher un club
 * 
 * @author eric.taix@gmail.com
 */
@SuppressLint("DefaultLocale")
@EFragment(value = R.layout.club_detail_layout)
public class ClubFragment extends Fragment implements OnItemClickListener {

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
	// Options de l'ImageLoader pour les logo des clubs: cache mémoire + cache disque
	private static DisplayImageOptions logoOptions = new DisplayImageOptions.Builder().showStubImage(R.drawable.empty).showImageForEmptyUri(R.drawable.empty)
			.cacheInMemory().build();

	private QuickAction quickAction;
	// Adpater qui affiche la liste des équipes
	private SimpleEquipesAdapter equipeAdapter;
	// Adapter qui contient les autres adapters
	private SectionAdapter sectionAdapter;
	// Adapteur pour les événements
	private EventAdapter eventAdapter;

	@AfterViews
	public void afterViews() {
		// If the fragment has been launched with an extra (it SHOULD be the cas)
		Bundle extras = getArguments();
		if (extras != null) {
			Parcelable clubToShow = extras.getParcelable(EXTRA_CLUB);
			if (clubToShow != null) {
				currentClub = (Club) clubToShow;
			}
		}
		
		// Then create all required adapters
		sectionAdapter = new SectionAdapter(this.getActivity(), R.layout.list_header);

		ClubInformationAdapter informationAdapter = new ClubInformationAdapter(this.getActivity(), currentClub);
		sectionAdapter.addSection(new Section(1,"INFORMATIONS", informationAdapter));

		ContactAdapter contactAdapter = new ContactAdapter(this.getActivity());
		contactAdapter.addContact(new ContactClub(currentClub));
		CollapsableAdapter collapseContact = new CollapsableAdapter(this.getActivity(), contactAdapter, sectionAdapter);
		sectionAdapter.addSection(new Section(2,"CONTACT", collapseContact));

		equipeAdapter = new SimpleEquipesAdapter(this.getActivity());
		CollapsableAdapter collapseEquipe = new CollapsableAdapter(this.getActivity(), equipeAdapter, sectionAdapter);
		collapseEquipe.setTexts("Toutes les équipes", "Réduire");
		sectionAdapter.addSection(new Section(3,"EQUIPES", collapseEquipe));

		eventAdapter = new EventAdapter(this.getActivity());
		CollapsableAdapter collapseEvent = new CollapsableAdapter(this.getActivity(), eventAdapter, sectionAdapter);
		collapseEvent.setTexts("Tous les évènements", "Réduire");
		sectionAdapter.addSection(new Section(4,"CALENDRIER", collapseEvent));

		listView.setCacheColorHint(getResources().getColor(R.color.transparent));
		
		// Set a better L&F for the divider
		int[] colors = { 0, 0xFF777777, 0 };
		listView.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		listView.setDividerHeight(0);
		listView.setAdapter(sectionAdapter);
		// On fixe les infos
		title.setText(currentClub.nomCourt);
		if (currentClub.urlLogo != null && currentClub.urlLogo.length() > 0) {
			logo.setVisibility(View.VISIBLE);
			// Display the current club's logo
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(currentClub.urlLogo, logo, logoOptions);
		}
		else {
			logo.setVisibility(View.GONE);
		}
		favorite.setImageDrawable(currentClub.favorite ? getResources().getDrawable(R.drawable.ic_star_enabled) : getResources().getDrawable(
				R.drawable.ic_star_disabled));
		listView.setOnItemClickListener(this);
		
		quickAction = new QuickAction(this.getActivity());
		contactAdapter.setContactQuickAction(quickAction);
		if (currentClub.mail != null && currentClub.mail.length() > 0) {
			ActionItem mailAction = new ActionItem(ID_MAIL, "Envoyer un email", getResources().getDrawable(R.drawable.ic_mail), new MailAction(
					currentClub.mail, currentClub.nom));
			quickAction.addActionItem(mailAction);
		}
		if (currentClub.telephone != null && currentClub.telephone.length() > 0) {
			ActionItem phoneAction = new ActionItem(ID_PHONE, "Téléphoner", getResources().getDrawable(R.drawable.ic_phone), new PhoneAction(
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
		quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				ActionItem actionItem = quickAction.getActionItem(pos);
				Action action = actionItem.getAction();
				executeAction(action);
			}
		});
		updateUI();
		// In the background request data from the network
		progressBar.setVisibility(View.VISIBLE);
		updateFromNetwork();
	}

	/**
	 * Click on the favorite button
	 */
	@Click(R.id.favorite)
	public void favoriteClicked() {
		int id_favorite = currentClub.favorite ? R.drawable.ic_star_disabled : R.drawable.ic_star_enabled;
		favorite.setImageDrawable(getResources().getDrawable(id_favorite));
		currentClub.favorite = !currentClub.favorite;
		updateClub(currentClub);
		String msg = currentClub.favorite ? currentClub.nomCourt + " a été ajouté aux favoris" : currentClub.nomCourt + " a été supprimé des favoris";
		Toast.makeText(this.getActivity(), msg, Toast.LENGTH_LONG).show();
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
	 * Upddate the UI according to the datas stored in the DB
	 */
	@UiThread
	void updateUI() {
		maj.setText(VolleyDatabaseHelper.getLastUpdate(updateDao, "EQUIPES-CLUB-" + currentClub.code));

		// Update the teams list for the current club (from the Database)
		try {
			List<Equipe> equipes = equipeDao.queryForEq("code_club", currentClub.code);
			if (equipes != null) {
				equipeAdapter.setEquipes(equipes);
				sectionAdapter.notifyDataSetChanged();
			}
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while retrieving teams list for club " + currentClub.code);
		}

		// Update calendars (from the DB)
		try {
			List<Event> events = eventDao.queryForEq("code", "CLUB-" + currentClub.code);
			eventAdapter.setEvents(events);
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while retrieving events list for club " + currentClub.code);
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
			List<Event> oldEvents = eventDao.queryForEq("code", "CLUB-" + codeClub);
			eventDao.delete(oldEvents);
			// The insert new datas
			List<Event> events = er.events;
			for (Event event : events) {
				event.code = "CLUB-" + codeClub;
				CreateOrUpdateStatus status = eventDao.createOrUpdate(event);
				Log.d("Volley34", "Nb of changed lines: " + status.getNumLinesChanged());
			}
		}
		catch (Exception e) {
			Log.e("Volley34", "Error while retrieving (from network) events list for club " + codeClub);
		}
	}

	@Background
	public void executeAction(Action action) {
		if (action != null) {
			action.execute(ClubFragment.this.getActivity());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Object obj = parent.getAdapter().getItem(position);
		// On affiche le détail d'une équipe
		if (obj instanceof Equipe) {
			EquipeFragment.showEquipe(this, ((Equipe) obj).codeEquipe, 0);
		}
	}
	
	/**
	 * Utility method to launch the fragment details of a club
	 * @param currentActivity
	 * @param codeEquipe
	 * @param requestCode
	 */
	public static void showClub(Fragment currentFragment, String codeClub, int requestCode) {
		MenuActivity activity = (MenuActivity)currentFragment.getActivity();
		// Set the argument
		Bundle extras = new Bundle();
		extras.putString(ClubFragment.EXTRA_CLUB, codeClub);
		// Create the fragment the switch the current content
		ClubFragment_ fragment = new ClubFragment_();
		fragment.setArguments(extras);
		activity.switchContent(null, fragment);
	}

}
