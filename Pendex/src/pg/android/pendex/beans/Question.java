package pg.android.pendex.beans;

import java.util.Set;

/**
 * Questions object to interact with the mask the database.
 * 
 * @author Sora
 *
 */
public class Question {

	private String id;
	private int random;
	private String question;
	private Set<Answer> answers;
	
	public Question() {			
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public int getRandom() {
		return random;
	}

	public void setRandom(final int random) {
		this.random = random;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(final String question) {
		this.question = question;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(final Set<Answer> answers) {
		this.answers = answers;
	}
	
}
