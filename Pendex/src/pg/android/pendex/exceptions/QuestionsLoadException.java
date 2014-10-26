package pg.android.pendex.exceptions;

/**
 * Exception while loading profile.
 * 
 * @author Sora
 *
 */
public class QuestionsLoadException extends Exception {

	/** Serial id. */
	private static final long serialVersionUID = 1L;

	public QuestionsLoadException() {
		super("Failed to load questions.");
	}

}
