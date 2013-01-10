/**
 * 
 */
package org.jared.android.volley.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.model.Contact;
import org.jared.android.volley.ui.widget.quickaction.QuickAction;
import org.jared.android.volley.utils.MD5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author eric.taix@gmail.com
 */
public class ContactAdapter extends BaseAdapter {

	protected List<Contact> contacts;
	protected Context ctx;
	private QuickAction qa;

	// Options de l'ImageLoader pour l'image des contacts: cache mémoire + cache disque
	private static DisplayImageOptions avatarOptions = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_unknown)
			.showImageForEmptyUri(R.drawable.ic_unknown).cacheInMemory().build();

	/**
	 * Constructeur permettant d'initialiser le nb max d'items à la valeur par défaut
	 * 
	 * @param ctx
	 * @param maxItems
	 */
	public ContactAdapter(Context ctx) {
		this.ctx = ctx;
	}

	/**
	 * Fixe le contact
	 * 
	 * @param contacts
	 */
	public void setContact(Contact contact) {
		List<Contact> cs = new ArrayList<Contact>();
		cs.add(contact);
		cs.add(contact);
		cs.add(contact);
		cs.add(contact);
		cs.add(contact);
		cs.add(contact);
		setContact(cs);
	}

	/**
	 * Fixe la liste des contacts
	 * 
	 * @param contacts
	 */
	public void setContact(List<Contact> contacts) {
		this.contacts = contacts;
		notifyDataSetChanged();
	}

	public void setContactQuickAction(QuickAction qa) {
		this.qa = qa;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.BaseAdapter#areAllItemsEnabled()
	 */
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.BaseAdapter#isEnabled(int)
	 */
	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (contacts != null) {
			return contacts.size();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (contacts != null && position < contacts.size()) {
			return contacts.get(position);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@SuppressLint("DefaultLocale")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.club_contact_layout, parent, false);
		}
		ImageView imgPhone = (ImageView) convertView.findViewById(R.id.img_phone);
		TextView phone = (TextView) convertView.findViewById(R.id.phone);
		ImageView imgMail = (ImageView) convertView.findViewById(R.id.img_mail);
		TextView mail = (TextView) convertView.findViewById(R.id.mail);
		TextView nom = (TextView) convertView.findViewById(R.id.nom);
		ImageView avatar = (ImageView) convertView.findViewById(R.id.portrait);

		// On récupère l'élément à afficher
		Contact contact = contacts.get(position);
		if (contact != null) {
			// Le button et l'action sur ce bouton
			Button button = (Button) convertView.findViewById(R.id.button_contact);
			final View anchorView = convertView;
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					qa.show(anchorView);
				}
			});
			// Les informations à afficher
			nom.setText(contact.getNom());
			if ((contact.getTelephone() != null && contact.getTelephone().trim().length() > 0)
					|| (contact.getMobile() != null && contact.getMobile().trim().length() > 0)) {
				String t = contact.getMobile();
				if (t == null || t.trim().length() == 0) {
					t = contact.getTelephone();
				}
				phone.setText(t);
				phone.setVisibility(View.VISIBLE);
				imgPhone.setVisibility(View.VISIBLE);
			}
			else {
				phone.setVisibility(View.GONE);
				imgPhone.setVisibility(View.GONE);
			}

			if (contact.getMail() != null) {
				mail.setText(contact.getMail());
				mail.setVisibility(View.VISIBLE);
				imgMail.setVisibility(View.VISIBLE);
				// On calcule le MD5 du mail
				String md5 = MD5.calcMD5(contact.getMail().trim().toLowerCase());
				String url = "http://www.gravatar.com/avatar/" + md5 + "?d=mm";
				ImageLoader imageLoader = ImageLoader.getInstance();
				imageLoader.displayImage(url, avatar, avatarOptions);
			}
			else {
				mail.setVisibility(View.VISIBLE);
				imgMail.setVisibility(View.VISIBLE);
			}
		}
		return convertView;
	}

}
