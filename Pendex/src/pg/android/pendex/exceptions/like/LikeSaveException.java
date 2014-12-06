package pg.android.pendex.exceptions.like;

import pg.android.pendex.exceptions.AbstractSaveException;

/**
 * Achievement load exception
 * 
 * @author Sora
 * 
 */
public class LikeSaveException extends AbstractSaveException {

    /** Serial id. */
    private static final long serialVersionUID = 1L;

    public LikeSaveException() {
        super("Likes failed to save.");
    }

}
