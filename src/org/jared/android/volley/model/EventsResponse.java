package org.jared.android.volley.model;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="p_GetAll_CalendarEventFilteredClub")
public class EventsResponse {
    @ElementList(inline=true)
	public List<Event> events;
}
