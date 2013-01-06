/**
 * 
 */
package org.jared.android.volley.ui.adapter;

import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.model.Equipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adpater permettant de gérer les items des clubs<br/>
 * 
 * @author eric.taix@gmail.com
 */
public class EquipeAdapter extends BaseAdapter {

	// ============== Inner Class ==============

	/**
	 * ViewHolder permettant d'afficher les informations du club
	 * 
	 * @author eric.taix@gmail.com
	 */
	class EquipeHolder {
		private TextView nomClub;
		private TextView title;
		private ImageView favoriteImg;

		public EquipeHolder(View view) {
			nomClub = (TextView) view.findViewById(R.id.nom_club);
			title = (TextView) view.findViewById(R.id.title);
			favoriteImg = (ImageView) view.findViewById(R.id.favorite);
			view.setTag(this);
		}

		public void update(Equipe equipe) {
			nomClub.setText(equipe.nomClub);
			title.setText(equipe.nomEquipe);
			favoriteImg.setVisibility((equipe.favorite ? View.VISIBLE : View.INVISIBLE));
		}

	}

	// =========================================

	private List<Equipe> equipes;
	private Context ctx;

	public EquipeAdapter(Context ctx) {
		this.ctx = ctx;
	}

	public EquipeAdapter(Context ctx, List<Equipe> equipesP) {
		this.ctx = ctx;
		setEquipes(equipesP);
	}

	/**
	 * Fixe le nouveau menu
	 * 
	 * @param menusP
	 */
	public void setEquipes(List<Equipe> equipesP) {
		equipes = equipesP;
		notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (equipes != null) {
			return equipes.size();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (equipes != null && position < equipes.size()) {
			return equipes.get(position);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		EquipeHolder holder = null;
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.equipe_item, parent, false);
			holder = new EquipeHolder(convertView);
		}
		else {
			holder = (EquipeHolder) convertView.getTag();
		}
		Equipe equipe = (Equipe) getItem(position);
		if (equipe != null) {
			holder.update(equipe);
		}
		return convertView;
	}
}
