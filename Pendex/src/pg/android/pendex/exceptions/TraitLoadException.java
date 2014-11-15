package pg.android.pendex.exceptions;

/**
 * Exception when trying to create an exiswting profile.
 * 
 * @author Sora
 * 
 */
public class TraitLoadException extends Exception {

    /** Serial id. */
    private static final long serialVersionUID = 1L;

    public TraitLoadException() {
        super("The traits failed to load.");
    }

}
