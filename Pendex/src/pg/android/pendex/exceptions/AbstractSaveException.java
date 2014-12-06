package pg.android.pendex.exceptions;

/**
 * Parent class for saving exceptions.
 * 
 * @author Sora
 * 
 */
public abstract class AbstractSaveException extends Exception {

    /** Serial id. */
    private static final long serialVersionUID = 1L;

    /**
     * Use this to set exception text.
     * 
     * @param exceptionText - String - Exception text.
     */
    public AbstractSaveException(final String exceptionText) {
        super(exceptionText);
    }

}
