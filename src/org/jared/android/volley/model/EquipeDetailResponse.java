/**
 * 
 */
package org.jared.android.volley.model;

import org.jared.android.volley.model.converter.EquipeDetailConverter;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

/**
 * Le modèle contenant un ensemble de clubs
 * @author eric.taix@gmail.com
 */
@Root(name="EQUIPE", strict=false)
public class EquipeDetailResponse {
	
    @Element(name="Row")
    @Convert(EquipeDetailConverter.class)
	private EquipeDetail equipeDetail;
    
    public EquipeDetail getEquipeDetail() {
    	return equipeDetail;
    }
}
