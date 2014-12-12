package pg.android.pendex.exceptions.profile;

/**
 * Exception when trying to create an exiswting profile.
 * 
 * @author Sora
 * 
 */
public class ProfileCreateException extends Exception {

    /** Serial id. */
    private static final long serialVersionUID = 1L;

    public ProfileCreateException() {
        super("A profile needs to be created before continuing.");
    }

}
