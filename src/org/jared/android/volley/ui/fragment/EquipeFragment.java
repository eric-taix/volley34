/**
 * 
 */
package org.jared.android.volley.ui.fragment;

import java.sql.SQLException;
import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.VolleyApplication;
import org.jared.android.volley.model.ClubInformation;
import org.jared.android.volley.model.Equipe;
import org.jared.android.volley.model.EquipeDetail;
import org.jared.android.volley.model.EquipeDetailResponse;
import org.jared.android.volley.model.Event;
import org.jared.android.volley.model.EventsResponse;
import org.jared.android.volley.model.Update;
import org.jared.android.volley.repository.VolleyDatabaseHelper;
import org.jared.android.volley.ui.MenuActivity;
import org.jared.android.volley.ui.adapter.ContactAdapter;
import org.jared.android.volley.ui.adapter.EventAdapter;
import org.jared.android.volley.ui.adapter.GymnaseAdapter;
import org.jared.android.volley.ui.adapter.SimpleClubsAdapter;
import org.jared.android.volley.ui.adapter.commons.CollapsableAdapter;
import org.jared.android.volley.ui.adapter.commons.Section;
import org.jared.android.volley.ui.adapter.commons.SectionAdapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
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

/**
 * Activité permettant d'afficher un club
 * 
 * @author eric.taix@gmail.com
 */
@SuppressLint("DefaultLocale")
@EFragment(value = R.layout.equipe_detail_layout)
public class EquipeFragment extends Fragment implements OnItemClickListener {

	private static final int ID_SECTION_ADR_CAL = 6;
	private static final int ID_SECTION_ADR_COUPE = 5;
	private static final int ID_SECTION_ADR_CHAMP = 4;
	private static final int ID_SECTION_CTX_COUPE = 3;
	private static final int ID_SECTION_CTX_CHAMP = 2;
	private static final int ID_SECTION_CLUB = 1;

	private static final String SECTION_ADRESSE = "GYMNASE";
	private static final String SECTION_ADRESSE_CHAMPIONNAT = "GYMNASE  CHAMPIONNAT";
	private static final String SECTION_ADRESSE_COUPE = "GYMNASE  COUPE";
	private static final String SECTION_CONTACTS_COUPE = "CONTACTS  COUPE";
	private static final String SECTION_CONTACTS_CHAMPIONNAT = "CONTACTS  CHAMPIONNAT";
	private static final String SECTION_CONTACTS = "CONTACTS";
	
	private static final String EXTRA_CODE_EQUIPE = "CODE_EQUIPE";

	public static final int ID_MAIL = 0;
	public static final int ID_PHONE = 1;
	public static final int ID_SMS = 2;
	public static final int ID_CONTACT = 3;
	public static final int ID_SHARE = 4;

	@App
	VolleyApplication application;
	@ViewById(R.id.title)
	TextView title;
	@ViewById(R.id.favorite)
	ImageView favorite;
	@ViewById(R.id.listView)
	ListView listView;
	@ViewById(R.id.maj)
	TextView maj;
	@ViewById(R.id.progressBar)
	ProgressBar progressBar;

	@OrmLiteDao(helper = VolleyDatabaseHelper.class, model = Update.class)
	Dao<Update, String> updateDao;
	@OrmLiteDao(helper = VolleyDatabaseHelper.class, model = Equipe.class)
	Dao<Equipe, String> equipeDao;
	@OrmLiteDao(helper = VolleyDatabaseHelper.class, model = EquipeDetail.class)
	Dao<EquipeDetail, String> equipeDetailDao;
	@OrmLiteDao(helper = VolleyDatabaseHelper.class, model = Event.class)
	Dao<Event, String> eventDao;

	// L'équipe courante
	private Equipe currentEquipe;

	// Adapter qui contient les autres adapters
	private SectionAdapter sectionAdapter;
	// Adapteur pour les contacts (championnat + coupe)
	private ContactAdapter contactChampionnatAdapter;
	private ContactAdapter contactCoupeAdapter;
	// Adapteurs pour les gymnases
	private GymnaseAdapter gymnaseChampionnatAdapter;
	private GymnaseAdapter gymnaseCoupeAdapter;
	// Adapteur pour le club
	private SimpleClubsAdapter clubAdapter;
	// Adapteur pour les événements
	private EventAdapter eventAdapter;

	@AfterViews
	public void afterViews() {
		sectionAdapter = new SectionAdapter(this.getActivity(), R.layout.list_header);

		clubAdapter = new SimpleClubsAdapter(this.getActivity());
		sectionAdapter.addSection(new Section(ID_SECTION_CLUB, "CLUB", clubAdapter));

		contactChampionnatAdapter = new ContactAdapter(this.getActivity());
		CollapsableAdapter collapseChampionnatContact = new CollapsableAdapter(this.getActivity(), contactChampionnatAdapter, sectionAdapter, 1);
		collapseChampionnatContact.setTexts("Tous les contacts", "Réduire");
		sectionAdapter.addSection(new Section(ID_SECTION_CTX_CHAMP, SECTION_CONTACTS_CHAMPIONNAT, collapseChampionnatContact));
		contactCoupeAdapter = new ContactAdapter(this.getActivity());
		CollapsableAdapter collapseCoupeContact = new CollapsableAdapter(this.getActivity(), contactCoupeAdapter, sectionAdapter, 1);
		collapseCoupeContact.setTexts("Tous les contacts", "Réduire");
		sectionAdapter.addSection(new Section(ID_SECTION_CTX_COUPE, SECTION_CONTACTS_COUPE, collapseCoupeContact));

		gymnaseChampionnatAdapter = new GymnaseAdapter(this.getActivity());
		sectionAdapter.addSection(new Section(ID_SECTION_ADR_CHAMP, SECTION_ADRESSE_CHAMPIONNAT, gymnaseChampionnatAdapter));
		gymnaseCoupeAdapter = new GymnaseAdapter(this.getActivity());
		sectionAdapter.addSection(new Section(ID_SECTION_ADR_COUPE, SECTION_ADRESSE_COUPE, gymnaseCoupeAdapter));

		eventAdapter = new EventAdapter(this.getActivity());
		sectionAdapter.addSection(new Section(ID_SECTION_ADR_CAL, "CALENDRIER", eventAdapter));

		listView.setCacheColorHint(getResources().getColor(R.color.transparent));
		// On positionne un divider plus "sympa"
		int[] colors = { 0, getResources().getColor(R.color.emphasis), 0 };
		listView.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		listView.setDividerHeight(1);
		listView.setAdapter(sectionAdapter);
		listView.setOnItemClickListener(this);
		
		// If the activity has been laucnhed with an extra containing the team code (It SHOULD be)
		Bundle extras = getArguments();
		if (extras != null) {
			String codeEquipe = extras.getString(EXTRA_CODE_EQUIPE);
			if (codeEquipe != null) {
				updateUI(codeEquipe);
				// En tâche de fond on interroge le serveur
				progressBar.setVisibility(View.VISIBLE);
				updateFromNetwork(codeEquipe);
			}
		}
	}

	/**
	 * The favorite button has been clicked
	 */
	@Click(R.id.favorite)
	public void favoriteClicked() {
		int id_favorite = currentEquipe.favorite ? R.drawable.ic_star_disabled : R.drawable.ic_star_enabled;
		favorite.setImageDrawable(getResources().getDrawable(id_favorite));
		currentEquipe.favorite = !currentEquipe.favorite;
		try {
			equipeDao.createOrUpdate(currentEquipe);
			String msg = currentEquipe.favorite ? currentEquipe.nomEquipe + " a été ajouté aux favoris" : currentEquipe.nomEquipe
					+ " a été supprimé des favoris";
			Toast.makeText(this.getActivity(), msg, Toast.LENGTH_LONG).show();
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while updating the team", e);
		}
	}

	// ---------------------- Update UI from the DB --------------------------

	@UiThread
	void updateUIAsync(String codeEquipe) {
		updateUI(codeEquipe);
	}

	void updateUI(String codeEquipe) {
		progressBar.setVisibility(View.GONE);
		maj.setText(VolleyDatabaseHelper.getLastUpdate(updateDao, "EQUIPE-" + codeEquipe));

		try {
			currentEquipe = equipeDao.queryForId(codeEquipe);
			if (currentEquipe != null) {
				ClubInformation ci = new ClubInformation();
				ci.nom = currentEquipe.nomClub;
				ci.code = currentEquipe.codeClub;
				clubAdapter.setClub(ci);
				// Set team informations
				title.setText(currentEquipe.nomEquipe);
				favorite.setImageDrawable(currentEquipe.favorite ? getResources().getDrawable(R.drawable.ic_star_enabled) : getResources().getDrawable(
						R.drawable.ic_star_disabled));
				// Update details from the DB
				updateDetailUI(currentEquipe.codeEquipe);
				// Update events from the DB
				updateCalendarUI(codeEquipe);
			}
		}
		catch (Exception e) {
			Log.e("Volley34", "Error while retrieving team from DB", e);
		}
	}

	/**
	 * Update the detail of a team (contacts and locations)
	 * @param codeEquipe
	 */
	private void updateDetailUI(String codeEquipe) {
		// Get current datas from DB and delete
		EquipeDetail ed;
		try {
			ed = equipeDetailDao.queryForId(codeEquipe);
			if (ed != null) {
				//-------- Contacts
				boolean showContactChamp = false;
				contactChampionnatAdapter.clear();
				if (!ed.contactRespChampionnat.isEmpty()) {
					contactChampionnatAdapter.addContact(ed.contactRespChampionnat);
					showContactChamp = true;
				}
				if (!ed.contactSupplChampionnat.isEmpty()) {
					contactChampionnatAdapter.addContact(ed.contactSupplChampionnat);
					showContactChamp = true;
				}
				boolean showContactCoupe = false;
				contactCoupeAdapter.clear();
				if (!ed.contactRespCoupe.isEmpty()) {
					contactCoupeAdapter.addContact(ed.contactRespCoupe);
					showContactCoupe = true;
				}
				if (!ed.contactSupplCoupe.isEmpty()) {
					contactCoupeAdapter.addContact(ed.contactSupplCoupe);
					showContactCoupe = true;
				}
				// If both contacts between championship and the cup are equals then change the title and hide one
				String title = SECTION_CONTACTS_CHAMPIONNAT;
				if (ed.contactRespChampionnat.equals(ed.contactRespCoupe) && ed.contactSupplChampionnat.equals(ed.contactSupplCoupe)) {
					title = SECTION_CONTACTS;
					showContactCoupe = false;
				}
				
				sectionAdapter.renameSection(ID_SECTION_CTX_CHAMP, title);
				sectionAdapter.setSectionVisibility(ID_SECTION_CTX_CHAMP, showContactChamp);
				sectionAdapter.setSectionVisibility(ID_SECTION_CTX_COUPE, showContactCoupe);


				//---------- Gymnasium
				
				if (!ed.gymnaseChampionnat.isEmpty()) {
					gymnaseChampionnatAdapter.setGymnase(ed.gymnaseChampionnat);

				}
				if (!ed.gymnaseCoupe.isEmpty()) {
					gymnaseCoupeAdapter.setGymnase(ed.gymnaseCoupe);

				}

				// If both gymnasium are equal then only display one and change its title
				if (ed.gymnaseChampionnat.equals(ed.gymnaseCoupe)) {
					sectionAdapter.renameSection(ID_SECTION_ADR_CHAMP, SECTION_ADRESSE);
					sectionAdapter.setSectionVisibility(ID_SECTION_ADR_COUPE, false);
				}
				else {
					sectionAdapter.renameSection(ID_SECTION_ADR_CHAMP, SECTION_ADRESSE_CHAMPIONNAT);
					sectionAdapter.setSectionVisibility(ID_SECTION_ADR_COUPE, true);
				}

				// Remove all adapters which are not used
				if (gymnaseChampionnatAdapter.getCount() == 0) {
					sectionAdapter.setSectionVisibility(ID_SECTION_ADR_CHAMP, false);
				}
				if (gymnaseCoupeAdapter.getCount() == 0) {
					sectionAdapter.setSectionVisibility(ID_SECTION_ADR_COUPE, false);
				}
			}
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while retrieving detail for team " + codeEquipe, e);
		}
	}

	/**
	 * Update team events fromt the DB
	 * @param codeEquipe
	 */
	public void updateCalendarUI(String codeEquipe) {
		List<Event> events;
		try {
			events = eventDao.queryForEq("code", "TEAM-" + codeEquipe);
			if (events != null) {
				eventAdapter.setEvents(events);
			}
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while retrieving events from DB for team " + codeEquipe, e);
		}
	}

	// ----------------------------- Network updates ----------------------------

	/**
	 * Retrieve team informations and launch other background process
	 * @param codeEquipe
	 */
	@Background
	void updateFromNetwork(String codeEquipe) {
		// Launch background updates for details and calendar
		try {
			updateDetailFromNetwork(codeEquipe);
			updateCalendarFromNetwork(codeEquipe);
			VolleyDatabaseHelper.updateLastUpdate(updateDao, "EQUIPE-" + codeEquipe);
			updateUIAsync(codeEquipe);
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while updating informations for team " + codeEquipe, e);
		}

	}

	/**
	 * Retrieve team details from network
	 * @throws SQLException
	 */
	public void updateDetailFromNetwork(String codeEquipe) throws SQLException {
		EquipeDetailResponse edr = application.restClient.getEquipeDetail(codeEquipe);
		EquipeDetail ed = edr.getEquipeDetail();

		// Get current datas from DB and delete
		EquipeDetail equipeDetail = equipeDetailDao.queryForId(codeEquipe);
		equipeDetailDao.delete(equipeDetail);

		// Add new informations
		equipeDetailDao.createOrUpdate(ed);
	}

	/**
	 * Retrieve team events from network
	 * @throws SQLException
	 */
	public void updateCalendarFromNetwork(String codeEquipe) throws SQLException {
		EventsResponse er = application.restClient.getCalendar(codeEquipe);
		// First delete all events which are connected to this club
		List<Event> oldEvents = eventDao.queryForEq("code", "TEAM-" + codeEquipe);
		eventDao.delete(oldEvents);
		// The insert new datas
		List<Event> events = er.events;
		for (Event event : events) {
			event.code = "TEAM-" + codeEquipe;
			CreateOrUpdateStatus status = eventDao.createOrUpdate(event);
			Log.d("Volley34", "Nb of changed lines: " + status.getNumLinesChanged());
		}
	}

	// --------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Object obj = parent.getAdapter().getItem(position);
		// Show the detail of a team
		if (obj instanceof ClubInformation) {
			ClubFragment.showClub(this, ((ClubInformation) obj).code, 0);
		}
	}

	/**
	 * Utility method to launch the fragment details of a team
	 * @param currentActivity
	 * @param codeEquipe
	 * @param requestCode
	 */
	public static void showEquipe(Fragment currentFragment, String codeEquipe, int requestCode) {
		MenuActivity activity = (MenuActivity) currentFragment.getActivity();
		// Set the argument
		Bundle extras = new Bundle();
		extras.putString(EquipeFragment.EXTRA_CODE_EQUIPE, codeEquipe);
		// Create the fragment the switch the current content
		EquipeFragment_ fragment = new EquipeFragment_();
		fragment.setArguments(extras);
		activity.switchContent(null, fragment);
	}
}
