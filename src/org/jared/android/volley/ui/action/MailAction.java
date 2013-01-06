/**
 * 
 */
package org.jared.android.volley.ui.action;

import org.jared.android.volley.ui.widget.quickaction.Action;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Action permettant d'envoyer un mail
 * 
 * @author eric.taix@gmail.com
 */
public class MailAction implements Action {
	
	private String mail;
	private String subject;

	public MailAction(String mail, String subject) {
		this.mail = mail;
		this.subject = subject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jared.android.volley.widget.quickaction.Action#execute(android.content.Context)
	 */
	@Override
	public void execute(Context context) {
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		String uri= "mailto:"+mail+"?subject="+subject;
		uri = uri.replace(" ", "%20");
		intent.setData(Uri.parse(uri));
		context.startActivity(Intent.createChooser(intent, "Envoyer un email..."));
	}

}
