/**
 * 
 */
package org.jared.android.volley.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Un événement (match, tournoi, fédéraux, réunion)
 * @author eric.taix@gmail.com
 */
@Root(name="Row",strict=false)
@DatabaseTable(tableName="events")
public class Event {

	public static final String TYPE_TOURNOI = "T";
	public static final String TYPE_MATCH = "M";
	public static final String TYPE_AUTRES = "A";
	public static final String TYPE_REUNION = "R";
	public static final String TYPE_FEDERAL = "F";

	@DatabaseField(columnName="id", generatedId=true)
	public int id;
	@DatabaseField(columnName="code")
	public String code;
	
	@DatabaseField(columnName="event_id")
	@Attribute(name = "CalendarEventID")
	public String eventId;
	@DatabaseField(columnName="nom_club")
	@Attribute(name = "NomClub")
	public String nom;
	@DatabaseField(columnName="penalite_locaux")
	@Attribute(name = "PenaliteLocaux")
	public String penaliteLocaux; // ???
	@DatabaseField(columnName="libelle_detail")
	@Attribute(name = "LibelleDetail")
	public String libelleDetail;
	@DatabaseField(columnName="gymnase_code")
	@Attribute(name = "GymnaseCode")
	public String gymnaseCode;
	@DatabaseField(columnName="equipe_visiteur_code")
	@Attribute(name = "EquipeVisiteursCode")
	public String equipeVisiteurCode;
	@DatabaseField(columnName="equipe_locaux_code")
	@Attribute(name = "EquipeLocauxCode")
	public String equipeLocaleCode;
	@DatabaseField(columnName="saison")
	@Attribute(name = "Saison")
	public String saison;
	@DatabaseField(columnName="competition_code")
	@Attribute(name = "CompetitionCode")
	public String competitionCode;
	@DatabaseField(columnName="libelle_match")
	@Attribute(name = "LibelleMatch")
	public String libelleMatch;
	@DatabaseField(columnName="match_code")
	@Attribute(name = "MatchCode")
	public String matchCode;
	@DatabaseField(columnName="event_code_club")
	@Attribute(name = "CodeClub")
	public String eventCodeClub;
	@DatabaseField(columnName="event_image_url")
	@Attribute(name = "CalendarEventImageURL")
	public String eventImageImageUrl;
	@DatabaseField(columnName="event_description")
	@Attribute(name = "CalendarEventDesciption")
	public String eventDescription;
	@DatabaseField(columnName="event_place")
	@Attribute(name = "CalendarEventPlace")
	public String eventPlace;
	@DatabaseField(columnName="event_fullday")
	@Attribute(name = "CalendarEventFullDay")
	public String eventFullDay;
	@DatabaseField(columnName="event_end_date")
	@Attribute(name = "CalendarEventEndDate")
	public String eventEndDate;
	@DatabaseField(columnName="event_start_date")
	@Attribute(name = "CalendarEventStartDate")
	public String eventStartDate;
	@DatabaseField(columnName="event_type")
	@Attribute(name = "CalendarEventType")
	public String eventType;
	@DatabaseField(columnName="event_name")
	@Attribute(name = "CalendarEventName")
	public String eventName;
	

}
