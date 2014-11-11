package pg.android.pendex.exceptions;

/**
 * Exception when trying to create an exiswting profile.
 * 
 * @author Sora
 * 
 */
public class ProfileExistsException extends Exception {

    /** Serial id. */
    private static final long serialVersionUID = 1L;

    public ProfileExistsException() {
        super("Profile failed to create new profile because one already exists.");
    }

}
