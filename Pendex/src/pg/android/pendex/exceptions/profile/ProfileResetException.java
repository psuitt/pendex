package pg.android.pendex.exceptions.profile;

/**
 * Exception while loading profile.
 * 
 * @author Sora
 * 
 */
public class ProfileResetException extends Exception {

	/** Serial id. */
	private static final long serialVersionUID = 1L;

	public ProfileResetException() {
		super("Profile failed to reset.");
	}

}
