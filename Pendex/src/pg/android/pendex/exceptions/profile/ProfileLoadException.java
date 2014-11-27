package pg.android.pendex.exceptions.profile;

/**
 * Exception while loading profile.
 * 
 * @author Sora
 *
 */
public class ProfileLoadException extends Exception {

	/** Serial id. */
	private static final long serialVersionUID = 1L;

	public ProfileLoadException() {
		super("Profile failed to load.");
	}

}
