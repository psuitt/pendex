package pg.android.pendex.exceptions.like;

/**
 * Achievement load exception
 * 
 * @author Sora
 * 
 */
public class LikeLoadException extends Exception {

    /** Serial id. */
    private static final long serialVersionUID = 1L;

    public LikeLoadException() {
        super("Likes failed to save.");
    }

}
