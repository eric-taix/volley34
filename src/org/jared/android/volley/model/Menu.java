/**
 * 
 */
package org.jared.android.volley.model;

/**
 * Un objet représentant un item du menu. 
 * @author eric.taix@gmail.com
 */
public class Menu {

	public String title;
	public int resourceId;
	
	public Menu() {
	}

	public Menu(String titleP, int resourceIdP) {
		title = titleP;
		resourceId = resourceIdP;
	}
}
