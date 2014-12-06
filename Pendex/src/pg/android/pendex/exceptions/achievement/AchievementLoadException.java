package pg.android.pendex.exceptions.achievement;

import pg.android.pendex.exceptions.AbstractLoadException;

/**
 * Achievement load exception
 * 
 * @author Sora
 * 
 */
public class AchievementLoadException extends AbstractLoadException {

    /** Serial id. */
    private static final long serialVersionUID = 1L;

    public AchievementLoadException() {
        super("Achievement failed to save.");
    }

}
