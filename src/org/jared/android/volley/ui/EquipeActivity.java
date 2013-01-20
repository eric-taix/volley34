/**
 * 
 */
package org.jared.android.volley.ui;

import java.util.Date;
import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.VolleyApplication;
import org.jared.android.volley.model.ClubInformation;
import org.jared.android.volley.model.Equipe;
import org.jared.android.volley.model.EquipeDetail;
import org.jared.android.volley.model.EquipeDetailResponse;
import org.jared.android.volley.repository.EquipeDAO;
import org.jared.android.volley.repository.MajDAO;
import org.jared.android.volley.repository.VolleyDatabase;
import org.jared.android.volley.ui.adapter.ContactAdapter;
import org.jared.android.volley.ui.adapter.GymnaseAdapter;
import org.jared.android.volley.ui.adapter.SimpleClubsAdapter;
import org.jared.android.volley.ui.adapter.commons.CollapsableAdapter;
import org.jared.android.volley.ui.adapter.commons.SectionAdapter;
import org.jared.android.volley.ui.widget.quickaction.Action;
import org.jared.android.volley.ui.widget.quickaction.ActionItem;
import org.jared.android.volley.ui.widget.quickaction.QuickAction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
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
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * Activité permettant d'afficher un club
 * 
 * @author eric.taix@gmail.com
 */
@SuppressLint("DefaultLocale")
@EActivity(value = R.layout.equipe_detail_layout)
public class EquipeActivity extends SherlockActivity implements OnItemClickListener {

	private static final String SECTION_ADRESSE_CHAMPIONNAT = "GYMNASE  CHAMPIONNAT";
	private static final String SECTION_ADRESSE_COUPE = "GYMNASE  COUPE";
	private static final String SECTION_CONTACTS_COUPE = "CONTACTS  COUPE";
	private static final String SECTION_CONTACTS_CHAMPIONNAT = "CONTACTS  CHAMPIONNAT";

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

	// L'équipe courante
	private Equipe currentEquipe;

	private QuickAction quickAction;

	// Adapter qui contient les autres adapters
	private SectionAdapter sectionAdapter;
	// Adapteur pour les contacts (championnat + coupe)
	private ContactAdapter contactChampionnatAdapter;
	private ContactAdapter contactCoupeAdapter;
	private GymnaseAdapter gymnaseChampionnatAdapter;
	private GymnaseAdapter gymnaseCoupeAdapter;
	private SimpleClubsAdapter clubAdapter;

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
		setTitle("Equipe");
	}

	@OptionsItem(android.R.id.home)
	boolean backHome() {
		finish();
		return true;
	}

	@AfterViews
	public void afterViews() {
		sectionAdapter = new SectionAdapter(this, R.layout.list_header);
		
		clubAdapter = new SimpleClubsAdapter(this);
		sectionAdapter.addSection("CLUB", clubAdapter);

		contactChampionnatAdapter = new ContactAdapter(this);
		CollapsableAdapter collapseChampionnatContact = new CollapsableAdapter(this, contactChampionnatAdapter, sectionAdapter, 1);
		collapseChampionnatContact.setTexts("Tous les contacts", "Réduire");
		sectionAdapter.addSection(SECTION_CONTACTS_CHAMPIONNAT, collapseChampionnatContact);
		contactCoupeAdapter = new ContactAdapter(this);
		CollapsableAdapter collapseCoupeContact = new CollapsableAdapter(this, contactCoupeAdapter, sectionAdapter, 1);
		collapseCoupeContact.setTexts("Tous les contacts", "Réduire");
		sectionAdapter.addSection(SECTION_CONTACTS_COUPE, collapseCoupeContact);

		gymnaseChampionnatAdapter = new GymnaseAdapter(this);
		sectionAdapter.addSection(SECTION_ADRESSE_CHAMPIONNAT, gymnaseChampionnatAdapter);
		gymnaseCoupeAdapter = new GymnaseAdapter(this);
		sectionAdapter.addSection(SECTION_ADRESSE_COUPE, gymnaseCoupeAdapter);

		ContactAdapter contactAdapter3 = new ContactAdapter(this);
		sectionAdapter.addSection("CALENDRIER", contactAdapter3);
		
		listView.setCacheColorHint(getResources().getColor(R.color.transparent));
		// On positionne un divider plus "sympa"
		int[] colors = { 0, 0xFF777777, 0 };
		listView.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		listView.setDividerHeight(0);
		listView.setAdapter(sectionAdapter);
		listView.setOnItemClickListener(this);
		// Création du menu pour le contact
		quickAction = new QuickAction(this);
		// if (currentClub.mail != null && currentClub.mail.length() > 0) {
		// ActionItem mailAction = new ActionItem(ID_MAIL, "Envoyer un email", getResources().getDrawable(R.drawable.ic_mail), new MailAction(
		// currentClub.mail, currentClub.nom));
		// quickAction.addActionItem(mailAction);
		// }
		// if (currentClub.telephone != null && currentClub.telephone.length() > 0) {
		// ActionItem phoneAction = new ActionItem(ID_PHONE, "Téléphoner", getResources().getDrawable(R.drawable.ic_phone), new PhoneAction(
		// currentClub.telephone));
		// ActionItem smsAction = new ActionItem(ID_SMS, "Envoyer un SMS", getResources().getDrawable(R.drawable.ic_sms), new SmsAction(currentClub.telephone));
		// quickAction.addActionItem(phoneAction);
		// quickAction.addActionItem(smsAction);
		// }
		// if ((currentClub.telephone != null && currentClub.telephone.length() > 0) || (currentClub.mail != null && currentClub.mail.length() > 0)) {
		// ActionItem contactAction = new ActionItem(ID_CONTACT, "Ajouter aux contacts", getResources().getDrawable(R.drawable.ic_address_book),
		// new ContactAction(currentClub));
		// ActionItem shareAction = new ActionItem(ID_SHARE, "Partager", getResources().getDrawable(R.drawable.ic_share), new ShareAction(currentClub));
		// quickAction.addActionItem(contactAction);
		// quickAction.addActionItem(shareAction);
		// }

		// Mise en place du listener sur le quick action
		quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				ActionItem actionItem = quickAction.getActionItem(pos);
				Action action = actionItem.getAction();
				executeAction(action);
			}
		});

		// En tâche de fond on interroge le serveur
		progressBar.setVisibility(View.VISIBLE);
		
		// Si l'activité est lancée avec des extras (ce qui devrait être le cas systématiquement)
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String codeEquipe = extras.getString(EXTRA_CODE_EQUIPE);
			if (codeEquipe != null) {
				updateEquipeFromDB(codeEquipe);
			}
		}
	}

	/**
	 * Click sur le bouton favorite
	 */
	@Click(R.id.favorite)
	public void favoriteClicked() {
		int id_favorite = currentEquipe.favorite ? R.drawable.ic_star_disabled : R.drawable.ic_star_enabled;
		favorite.setImageDrawable(getResources().getDrawable(id_favorite));
		currentEquipe.favorite = !currentEquipe.favorite;
		updateEquipe(currentEquipe);
		String msg = currentEquipe.favorite ? currentEquipe.nomEquipe + " a été ajouté aux favoris" : currentEquipe.nomEquipe + " a été supprimé des favoris";
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	// ---------------------- Les différents mises à jour DB + Network

	@Background
	void updateEquipeFromDB(String code) {
		VolleyDatabase db = new VolleyDatabase(this);
		Equipe equipe = EquipeDAO.getByCode(db, code);
		updateUI(equipe);
	}

	@UiThread
	void updateUI(Equipe equipe) {
		currentEquipe = equipe;
		ClubInformation ci = new ClubInformation();
		ci.nom = currentEquipe.nomClub;
		ci.code = currentEquipe.codeClub;
		clubAdapter.setClub(ci);
		// On fixe les infos
		title.setText(currentEquipe.nomEquipe);
		favorite.setImageDrawable(currentEquipe.favorite ? getResources().getDrawable(R.drawable.ic_star_enabled) : getResources().getDrawable(
				R.drawable.ic_star_disabled));
		// On lance la demande de détail
		retrieveDetailFromNetwork();
	}

	/**
	 * Récupère (si possible) le détail de l'équipe
	 */
	@Background
	public void retrieveDetailFromNetwork() {
		try {
			EquipeDetailResponse edr = application.restClient.getEquipeDetail(currentEquipe.codeEquipe);
			EquipeDetail ed = edr.getEquipeDetail();
			updateEquipe(ed);

			// VolleyDatabase db = new VolleyDatabase(this);
			// EquipeDAO.saveAll(db, ecl.equipes, currentClub.code);
			// MajDAO.udateMaj(db, "EQUIPES-CLUB-" + currentClub.code);
		}
		finally {
			// updateEquipesFromDB();
		}
	}

	@UiThread
	public void updateEquipe(EquipeDetail ed) {
		if (!ed.contactRespChampionnat.isEmpty()) {
			contactChampionnatAdapter.addContact(ed.contactRespChampionnat);
		}
		if (!ed.contactSupplChampionnat.isEmpty()) {
			contactChampionnatAdapter.addContact(ed.contactSupplChampionnat);
		}
		if (!ed.contactRespCoupe.isEmpty()) {
			contactCoupeAdapter.addContact(ed.contactRespCoupe);
		}
		if (!ed.contactSupplCoupe.isEmpty()) {
			contactCoupeAdapter.addContact(ed.contactSupplCoupe);
		}
		if (!ed.gymnaseChampionnat.isEmpty()) {
			gymnaseChampionnatAdapter.setGymnase(ed.gymnaseChampionnat);
		}
		if (!ed.gymnaseCoupe.isEmpty()) {
			gymnaseCoupeAdapter.setGymnase(ed.gymnaseCoupe);
		}

		// On fait le ménage dans les sections ne servant pas
		if (contactChampionnatAdapter.getCount() == 0) {
			sectionAdapter.removeSection(SECTION_CONTACTS_CHAMPIONNAT);
		}
		if (contactCoupeAdapter.getCount() == 0) {
			sectionAdapter.removeSection(SECTION_CONTACTS_COUPE);
		}
		if (gymnaseChampionnatAdapter.getCount() == 0) {
			sectionAdapter.removeSection(SECTION_ADRESSE_CHAMPIONNAT);
		}
		if (gymnaseCoupeAdapter.getCount() == 0) {
			sectionAdapter.removeSection(SECTION_ADRESSE_COUPE);
		}
	}

	@Background
	void updateEquipe(Equipe equipeToUpdate) {
		try {
			VolleyDatabase db = new VolleyDatabase(this);
			EquipeDAO.updateEquipe(db, equipeToUpdate);
		}
		finally {
		}
	}

	/**
	 * Met à jour l'interface graphique en fonction des données contenues dans la BD
	 */
	void updateUI() {
		VolleyDatabase db = new VolleyDatabase(this);
		// Date de maj
		Date dateMaj = MajDAO.getMaj(db, "EQUIPE-" + currentEquipe.codeEquipe);
		if (dateMaj != null) {
			maj.setText(DateUtils.getRelativeTimeSpanString(dateMaj.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS,
					DateUtils.FORMAT_NUMERIC_DATE));
		}
		else {
			maj.setText("");
		}
		List<Equipe> equipes = EquipeDAO.getAllEquipes(db, currentEquipe.codeEquipe);
		if (equipes != null) {
			sectionAdapter.notifyDataSetChanged();
		}
		progressBar.setVisibility(View.GONE);
	}

	@Background
	public void executeAction(Action action) {
		if (action != null) {
			action.execute(EquipeActivity.this);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// Le site Web
		if (position == 1) {
		}
		// Le contact
		else if (position == 3) {
			quickAction.show(view);
		}
		// Une des équipes
		else {

		}
	}

	/**
	 * Méthode utilitaire permettant de lancer l'activité de détail d'une équipe depuis une activité
	 * @param currentActivity
	 * @param codeEquipe
	 * @param requestCode
	 */
	public static void startActivityForResult(Activity currentActivity, String codeEquipe, int requestCode) {
		Intent intent = new Intent(currentActivity, EquipeActivity_.class);
		intent.putExtra(EquipeActivity.EXTRA_CODE_EQUIPE, codeEquipe);
		currentActivity.startActivityForResult(intent, requestCode);
	}

	/**
	 * Méthode utilitaire permettant de lancer l'activité de détail d'une équipe depuis un fragment
	 * @param currentActivity
	 * @param codeEquipe
	 * @param requestCode
	 */
	public static void startActivityForResult(Fragment currentFragment, String codeEquipe, int requestCode) {
		Intent intent = new Intent(currentFragment.getActivity(), EquipeActivity_.class);
		intent.putExtra(EquipeActivity.EXTRA_CODE_EQUIPE, codeEquipe);
		currentFragment.startActivityForResult(intent, requestCode);
	}
}
