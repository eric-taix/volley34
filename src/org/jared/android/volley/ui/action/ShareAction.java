/**
 * 
 */
package org.jared.android.volley.ui.action;

import org.jared.android.volley.model.Contact;
import org.jared.android.volley.ui.widget.quickaction.Action;

import android.content.Context;
import android.content.Intent;

/**
 * Action permettant de partager un contact
 * 
 * @author eric.taix@gmail.com
 */
public class ShareAction implements Action {

	private Contact contact;

	public ShareAction(Contact contact) {
		this.contact = contact;
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
		String body = "Contact: " + contact.getNom();
		if (contact.getMobile() != null && contact.getMobile().length() > 0) {
			body += " - Mobile: " +contact.getMobile();
		}
		if (contact.getMail() != null && contact.getMail().length() > 0) {
			body += " - Email: " + contact.getMail();
		}
		if (contact.getTelephone() != null && contact.getTelephone().length() > 0) {
			body += " - Téléphone: " + contact.getTelephone();
		}
		intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Contact");
		intent.putExtra(android.content.Intent.EXTRA_TEXT, body);
		context.startActivity(Intent.createChooser(intent, "Partager un contact..."));
	}

}
