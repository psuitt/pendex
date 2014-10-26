package pg.android.pendex.exceptions;

/**
 * Exception while loading profile.
 * 
 * @author Sora
 *
 */
public class ProfileSaveException extends Exception {

	/** Serial id. */
	private static final long serialVersionUID = 1L;

	public ProfileSaveException() {
		super("Profile failed to save.");
	}

}
