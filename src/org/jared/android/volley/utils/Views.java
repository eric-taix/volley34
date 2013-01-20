/**
 * 
 */
package org.jared.android.volley.utils;

import android.view.View;
import android.widget.TextView;

/**
 * Utilitaire sur des Strings
 * @author eric.taix@gmail.com
 */
public class Views {

	
	/**
	 * Affiche un texte dans un TextView ou rend invisible le TextView si la chaine ne contient aucune donnŽe
	 * @param tv
	 * @param s
	 */
	public static void showOrGone(TextView tv, String s) {
		if (s != null && s.trim().length() > 0) {
			tv.setText(s);
			tv.setVisibility(View.VISIBLE);
		}
		else {
			tv.setVisibility(View.GONE);
		}
	}
	
}
