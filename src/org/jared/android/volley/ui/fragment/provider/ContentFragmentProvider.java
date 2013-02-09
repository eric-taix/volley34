package org.jared.android.volley.ui.fragment.provider;

import org.jared.android.volley.http.RestClient;

import android.support.v4.app.Fragment;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;

/**
 * Interface permettant de fournir les informations n�cessaires au fragment de base du menu
 * @author eric.taix@gmail.com
 */
public interface ContentFragmentProvider extends OnItemClickListener {
	
	/**
	 * Initialise le fournisseur avec le context courant
	 * @param ctx
	 */
	public void init(Fragment act);
	
	/**
	 * Retourne l'adapteur pour la LiustView
	 * @return
	 */
	public ListAdapter getListAdapter();
	
	/**
	 * Retourne un code unique correspondant au code de MAJ
	 * @return
	 */
	public abstract String getCode();
	
	/**
	 * Retourne le titre
	 * @return
	 */
	public abstract String getTitle();
	
	/**
	 * Met � jour l'interface graphique � partir des donn�es de la base
	 * @param db
	 */
	public abstract void doUpdateUI();
	
	/**
	 * R�cup�re les donn�es � partir du serveur.
	 * @param client
	 * @return
	 */
	public abstract Object doGetFromNetwork(RestClient client);
	
	/**
	 * Sauve les donn�es dans la base de donn�es
	 * @param oject
	 * @param db
	 */
	public abstract void doSaveToDatabase(Object oject);
	
}
