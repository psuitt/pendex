package pg.android.pendex.exceptions.achievement;

import pg.android.pendex.exceptions.AbstractSaveException;

/**
 * Achievement load exception
 * 
 * @author Sora
 * 
 */
public class AchievementSaveException extends AbstractSaveException {

    /** Serial id. */
    private static final long serialVersionUID = 1L;

    public AchievementSaveException() {
        super("Achievement failed to save.");
    }

}
