package pg.android.pendex.exceptions.like;

import pg.android.pendex.exceptions.AbstractLoadException;

/**
 * Achievement load exception
 * 
 * @author Sora
 * 
 */
public class LikeLoadException extends AbstractLoadException {

    /** Serial id. */
    private static final long serialVersionUID = 1L;

    public LikeLoadException() {
        super("Likes failed to save.");
    }

}
