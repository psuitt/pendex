package pg.android.pendex.exceptions.like;

/**
 * Achievement load exception
 * 
 * @author Sora
 * 
 */
public class LikeSaveException extends Exception {

    /** Serial id. */
    private static final long serialVersionUID = 1L;

    public LikeSaveException() {
        super("Likes failed to save.");
    }

}
