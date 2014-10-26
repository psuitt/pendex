package pg.android.pendex.beans;

import java.util.Map;

/**
 * Pendex is used for creating a pendex for users.
 * 
 * @author Sora
 *
 */
public class PendexRating {

	private Map<String, Integer> pendex;
	
	public PendexRating() {
		
	}

	public Map<String, Integer> getPendex() {
		return pendex;
	}

	public void setPendex(final Map<String, Integer> pendex) {
		this.pendex = pendex;
	}
	
}
