/**
 * 
 */
package org.jared.android.volley.model;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * The model which contains updates informations
 * @author eric.taix@gmail.com
 */
@DatabaseTable(tableName="updates")
public class Update {

	@DatabaseField(columnName="code", id=true)
	public String code;
	@DatabaseField(columnName="date_time")
	public Date dateTime;
}
