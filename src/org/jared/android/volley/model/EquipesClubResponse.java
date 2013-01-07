/**
 * 
 */
package org.jared.android.volley.model;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Le modèle contenant un ensemble de clubs
 * @author eric.taix@gmail.com
 */
@Root(name="sp_GetAll_EquipeOfClub")
public class EquipesClubResponse {
    @ElementList(inline=true)
	public List<Equipe> equipes;
}
