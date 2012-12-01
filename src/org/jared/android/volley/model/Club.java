/**
 * 
 */
package org.jared.android.volley.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * DŽfinition d'un club
 * @author eric.taix@gmail.com
 */
@Root(name="Row",strict=false)
public class Club {
	@Attribute(name="CodeClub")
	public String code;
	@Attribute(name="NbEquipes")
	public int nbEquipes;
	@Attribute(name="URLLogoClub")
	public String urlLogo;
	@Attribute(name="MailClub")
	public String mail;
	@Attribute(name="URLClub")
	public String urlSiteWeb;
	@Attribute(name="TelephoneClub")
	public String telephone;
	@Attribute(name="ContactClub")
	public String contact;
	@Attribute(name="NomClub")
	public String nom;
	@Attribute(name="NomClubCourt")
	public String nomCourt;
	
	public boolean favorite = false;
}
