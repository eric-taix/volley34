/**
 * 
 */
package org.jared.android.volley.model;

import org.simpleframework.xml.Attribute;

/**
 * Un événement (match, tournoi, fédéraux, réunion)
 * @author eric.taix@gmail.com
 */
public class Event {

			@Attribute(name="NomClub")
			public String nom;
			@Attribute(name="PenaliteLocaux")
			public String penaliteLocaux; // ???
			@Attribute(name="LibelleDetail")
			public String libelleDetail;
			@Attribute(name="GymnaseCode")
			public String gymnaseCode;
			@Attribute(name="EquipeVisiteursCode")
			public String equipeVisiteurCode;
			@Attribute(name="EquipeLocauxCode")
			public String equipeLocaleCode;			
			@Attribute(name="Saison")
			public String saison;
			@Attribute(name="CompetitionCode")
			public String competitionCode;
			@Attribute(name="LibelleMatch")
			public String libelleMatch;
			@Attribute(name="MatchCode")
			public String matchCode;
			@Attribute(name="CodeClub")
			public String codeClub;
			@Attribute(name="CalendarEventImageURL")
			public String eventImageImageUrl;
			@Attribute(name="CalendarEventDesciption")
			public String eventDescription;
			@Attribute(name="CalendarEventPlace")
			public String eventPlace;
			@Attribute(name="CalendarEventFullDay")
			public String eventFullDay;
			@Attribute(name="CalendarEventEndDate")
			public String eventEndDate;
			@Attribute(name="CalendarEventStartDate")
			public String eventStartDate;
			@Attribute(name="CalendarEventType")
			public String eventType;
			@Attribute(name="CalendarEventName")
			public String eventName;
			@Attribute(name="CalendarEventID")
			public String eventId;

}
