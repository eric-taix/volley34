/**
 * 
 */
package org.jared.android.volley.ui;

import java.util.Date;
import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.http.RestClient;
import org.jared.android.volley.model.Equipe;
import org.jared.android.volley.repository.EquipeDAO;
import org.jared.android.volley.repository.MajDAO;
import org.jared.android.volley.repository.VolleyDatabase;
import org.jared.android.volley.ui.adapter.ClubInformationAdapter;
import org.jared.android.volley.ui.adapter.SectionAdapter;
import org.jared.android.volley.ui.widget.quickaction.Action;
import org.jared.android.volley.ui.widget.quickaction.ActionItem;
import org.jared.android.volley.ui.widget.quickaction.QuickAction;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;

/**
 * Activité permettant d'afficher un club
 * 
 * @author eric.taix@gmail.com
 */
@SuppressLint("DefaultLocale")
@EActivity(value = R.layout.equipe_detail_layout) 
public class EquipeActivity extends SherlockActivity implements OnItemClickListener {

	public static final String EXTRA_EQUIPE = "EQUIPE";

	public static final int ID_MAIL = 0;
	public static final int ID_PHONE = 1;
	public static final int ID_SMS = 2;
	public static final int ID_CONTACT = 3;
	public static final int ID_SHARE = 4;

	@RestService
	RestClient restClient;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ActionBar ab = getSupportActionBar();
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		setTitle("Club");
		// Si l'activité est lancée avec des extras (ce qui devrait être le cas systématiquement)
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Parcelable equipeToShow = extras.getParcelable(EXTRA_EQUIPE);
			if (equipeToShow != null) {
				currentEquipe = (Equipe) equipeToShow;
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
		ClubInformationAdapter informationAdapter = new ClubInformationAdapter(this);
		sectionAdapter = new SectionAdapter(this, R.layout.list_header);
		sectionAdapter.addSection("CLUB", informationAdapter);
		sectionAdapter.addSection("CONTACTS", informationAdapter);
		listView.setCacheColorHint(getResources().getColor(R.color.transparent));
		// On positionne un divider plus "sympa"
		int[] colors = { 0, 0xFF777777, 0 };
		listView.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		listView.setDividerHeight(1);
		listView.setAdapter(sectionAdapter);
		// On fixe les infos
		title.setText(currentEquipe.nomEquipe);
		favorite.setImageDrawable(currentEquipe.favorite ? getResources().getDrawable(R.drawable.ic_star_enabled) : getResources().getDrawable(
				R.drawable.ic_star_disabled));
		listView.setOnItemClickListener(this);
		// Création du menu pour le contact
		quickAction = new QuickAction(this);
//		if (currentClub.mail != null && currentClub.mail.length() > 0) {
//			ActionItem mailAction = new ActionItem(ID_MAIL, "Envoyer un email", getResources().getDrawable(R.drawable.ic_mail), new MailAction(
//					currentClub.mail, currentClub.nom));
//			quickAction.addActionItem(mailAction);
//		}
//		if (currentClub.telephone != null && currentClub.telephone.length() > 0) {
//			ActionItem phoneAction = new ActionItem(ID_PHONE, "Téléphoner", getResources().getDrawable(R.drawable.ic_phone), new PhoneAction(
//					currentClub.telephone));
//			ActionItem smsAction = new ActionItem(ID_SMS, "Envoyer un SMS", getResources().getDrawable(R.drawable.ic_sms), new SmsAction(currentClub.telephone));
//			quickAction.addActionItem(phoneAction);
//			quickAction.addActionItem(smsAction);
//		}
//		if ((currentClub.telephone != null && currentClub.telephone.length() > 0) || (currentClub.mail != null && currentClub.mail.length() > 0)) {
//			ActionItem contactAction = new ActionItem(ID_CONTACT, "Ajouter aux contacts", getResources().getDrawable(R.drawable.ic_address_book),
//					new ContactAction(currentClub));
//			ActionItem shareAction = new ActionItem(ID_SHARE, "Partager", getResources().getDrawable(R.drawable.ic_share), new ShareAction(currentClub));
//			quickAction.addActionItem(contactAction);
//			quickAction.addActionItem(shareAction);
//		}

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
		// En tâche de fond on interroge le serveur
		progressBar.setVisibility(View.VISIBLE);
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
		String msg = currentEquipe.favorite ? currentEquipe.nomEquipe + " a été ajouté aux favoris" : currentEquipe.nomEquipe+ " a été supprimé des favoris";
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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
			maj.setText(DateUtils.getRelativeTimeSpanString(dateMaj.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_NUMERIC_DATE));
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
	 * 
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

}
