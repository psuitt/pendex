package pg.android.pendex.exceptions.achievement;

/**
 * Achievement load exception
 * 
 * @author Sora
 * 
 */
public class AchievementLoadException extends Exception {

    /** Serial id. */
    private static final long serialVersionUID = 1L;

    public AchievementLoadException() {
        super("Achievement failed to save.");
    }

}
