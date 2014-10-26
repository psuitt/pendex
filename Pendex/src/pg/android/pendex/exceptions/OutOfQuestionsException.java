package pg.android.pendex.exceptions;

/**
 * Exception while loading profile.
 * 
 * @author Sora
 *
 */
public class OutOfQuestionsException extends Exception {

	/** Serial id. */
	private static final long serialVersionUID = 1L;

	public OutOfQuestionsException() {
		super("You have completed all questions.");
	}

}
