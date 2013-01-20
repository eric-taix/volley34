/**
 * 
 */
package org.jared.android.volley.model;


/**
 * Définition d'un club au minima à savoir son nom et son code
 * @author eric.taix@gmail.com
 */
public class ClubInformation {
	
	public String code;
	public String nom;

	/**
	 * Constructeur par défait
	 */
	public ClubInformation() {
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ClubInformation other = (ClubInformation) obj;
		if (code == null) {
			if (other.code != null) return false;
		}
		else if (!code.equals(other.code)) return false;
		return true;
	}
}
