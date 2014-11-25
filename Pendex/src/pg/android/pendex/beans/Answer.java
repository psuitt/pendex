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
    private String achievement;
    private String linkedType;
    private String linked;

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

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(final String achievement) {
        this.achievement = achievement;
    }

    public String getLinkedType() {
        return linkedType;
    }

    public void setLinkedType(final String linkedType) {
        this.linkedType = linkedType;
    }

    public String getLinked() {
        return linked;
    }

    public void setLinked(final String linked) {
        this.linked = linked;
    }

    @Override
    public String toString() {
        return "Answer [answer=" + answer + ", pendexRating=" + pendexRating + ", achievement="
                + achievement + ", linkedType=" + linkedType + ", linked=" + linked + "]";
    }

}
