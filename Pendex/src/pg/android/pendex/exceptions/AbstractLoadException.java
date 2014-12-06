package pg.android.pendex.exceptions;

/**
 * Parent class for loading exceptions.
 * 
 * @author Sora
 * 
 */
public abstract class AbstractLoadException extends Exception {

    /** Serial id. */
    private static final long serialVersionUID = 1L;

    /**
     * Use this to set exception text.
     * 
     * @param exceptionText - String - Exception text.
     */
    public AbstractLoadException(final String exceptionText) {
        super(exceptionText);
    }

}
