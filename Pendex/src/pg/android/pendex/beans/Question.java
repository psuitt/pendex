package pg.android.pendex.beans;

import java.util.List;

/**
 * Questions object to interact with the mask the database.
 * 
 * @author Sora
 * 
 */
public class Question {

    private String id;
    private String parentId;
    private int random;
    private String question;
    private String type;
    private List<Answer> answers;

    public Question() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(final String parentId) {
        this.parentId = parentId;
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

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(final List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", parentId=" + parentId + ", random=" + random
                + ", question=" + question + ", answers=" + answers + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Question other = (Question) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
