package pg.android.pendex.beans;

/**
 * Bean to warp answers and mask database structure.
 * 
 * @author Sora
 *
 */
public class Answer {

	private String answer;
	private PendexRating pendexRating;
	
	public Answer() {
		
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(final String answer) {
		this.answer = answer;
	}

	public PendexRating getPendexRating() {
		return pendexRating;
	}

	public void setPendexRating(final PendexRating pendexRating) {
		this.pendexRating = pendexRating;
	}
	
}
