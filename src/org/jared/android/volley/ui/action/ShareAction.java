/**
 * 
 */
package org.jared.android.volley.ui.action;

import org.jared.android.volley.model.Club;
import org.jared.android.volley.ui.widget.quickaction.Action;

import android.content.Context;
import android.content.Intent;

/**
 * Action permettant de partager un contact
 * 
 * @author eric.taix@gmail.com
 */
public class ShareAction implements Action {

	private Club club;

	public ShareAction(Club club) {
		this.club = club;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jared.android.volley.widget.quickaction.Action#execute(android.content.Context)
	 */
	@Override
	public void execute(Context context) {
		Intent intent = new Intent(android.content.Intent.ACTION_SEND);
		intent.setType("text/plain");
		String body = club.nom + " - Contact: " + club.contact;
		if (club.telephone != null && club.telephone.length() > 0) {
			body += " - TŽlŽphone: " + club.telephone;
		}
		if (club.mail != null && club.mail.length() > 0) {
			body += " - Email: " + club.mail;
		}
		intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Contact");
		intent.putExtra(android.content.Intent.EXTRA_TEXT, body);
		context.startActivity(Intent.createChooser(intent, "Partager un contact..."));
	}

}
