package pg.android.pendex.exceptions.achievement;

/**
 * Achievement load exception
 * 
 * @author Sora
 * 
 */
public class AchievementSaveException extends Exception {

    /** Serial id. */
    private static final long serialVersionUID = 1L;

    public AchievementSaveException() {
        super("Achievement failed to save.");
    }

}
