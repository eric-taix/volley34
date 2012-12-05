/**
 * 
 */
package org.jared.android.volley;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;

import org.jared.android.volley.adapter.ClubContactAdapter;
import org.jared.android.volley.adapter.ClubInformationAdapter;
import org.jared.android.volley.adapter.SectionAdapter;
import org.jared.android.volley.model.Club;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.ViewById;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Activité permettant d'afficher un club
 * 
 * @author eric.taix@gmail.com
 */
@EActivity(value = R.layout.club_detail_layout)
public class ClubActivity extends SherlockActivity implements OnItemClickListener {

	public static final String EXTRA_CLUB = "CLUB";

	public static final int ID_MAIL = 0;
	public static final int ID_PHONE = 1;
	public static final int ID_SMS = 2;
	public static final int ID_CONTACT = 3;
	public static final int ID_SHARE = 4;

	@ViewById(R.id.title)
	TextView title;
	@ViewById(R.id.logo)
	ImageView logo;
	@ViewById(R.id.favorite)
	ImageView favorite;

	@ViewById(R.id.listView)
	ListView listView;

	// Le club courant
	private Club currentClub;
	// Options de l'ImageLoader pour les logo des clubs: cache mémoire + cache disque
	private static DisplayImageOptions logoOptions = new DisplayImageOptions.Builder().showStubImage(R.drawable.empty).showImageForEmptyUri(R.drawable.empty)
			.cacheInMemory().build();

	private QuickAction quickAction;

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
		ClubContactAdapter contactAdapter = new ClubContactAdapter(this, currentClub);
		ClubInformationAdapter informationAdapter = new ClubInformationAdapter(this, currentClub);
		SectionAdapter sectionAdapter = new SectionAdapter(this, R.layout.list_header);
		sectionAdapter.addSection("INFORMATIONS", informationAdapter);
		sectionAdapter.addSection("CONTACT", contactAdapter);
		listView.setCacheColorHint(getResources().getColor(R.color.transparent));
		// On positionne un divider plus "sympa"
		int[] colors = { 0, 0xFF777777, 0 };
		listView.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		listView.setDividerHeight(1);
		listView.setAdapter(sectionAdapter);
		// On fixe les infos
		title.setText(currentClub.nomCourt);
		if (currentClub.urlLogo != null && currentClub.urlLogo.length() > 0) {
			logo.setVisibility(View.VISIBLE);
			// On affiche le logo du club en tâche de fond
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(currentClub.urlLogo, logo, logoOptions);
		}
		else {
			logo.setVisibility(View.INVISIBLE);
		}
		favorite.setImageDrawable(currentClub.favorite ? getResources().getDrawable(R.drawable.ic_star_enabled) : getResources().getDrawable(
				R.drawable.ic_star_disabled));
		listView.setOnItemClickListener(this);
		// Création du menu pour le contact
		ActionItem addItem = new ActionItem(ID_MAIL, "Envoyer un email", getResources().getDrawable(R.drawable.ic_mail));
		ActionItem acceptItem = new ActionItem(ID_PHONE, "Téléphoner", getResources().getDrawable(R.drawable.ic_phone));
		ActionItem uploadItem = new ActionItem(ID_SMS, "Envoyer un SMS", getResources().getDrawable(R.drawable.ic_sms));
		ActionItem contactItem = new ActionItem(ID_CONTACT, "Ajouter aux contacts", getResources().getDrawable(R.drawable.ic_address_book));
		ActionItem shareItem = new ActionItem(ID_SHARE, "Partager", getResources().getDrawable(R.drawable.ic_share));

		quickAction = new QuickAction(this);

		quickAction.addActionItem(addItem);
		quickAction.addActionItem(acceptItem);
		quickAction.addActionItem(uploadItem);
		quickAction.addActionItem(contactItem);
		quickAction.addActionItem(shareItem);

		// setup the action item click listener
		quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				ActionItem actionItem = quickAction.getActionItem(pos);

				if (actionId == ID_MAIL) {
					Toast.makeText(getApplicationContext(), "Add item selected", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(getApplicationContext(), actionItem.getTitle() + " selected", Toast.LENGTH_SHORT).show();
				}
			}
		});
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
			if (currentClub.urlSiteWeb != null && currentClub.urlSiteWeb.length() > 0) {
				String url = currentClub.urlSiteWeb;
				if (!url.toLowerCase().startsWith("http://") || !url.toLowerCase().startsWith("https://")) {
					url = "http://" + url;
				}
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				startActivity(intent);
			}
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
