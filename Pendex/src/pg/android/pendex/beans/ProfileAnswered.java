package pg.android.pendex.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Returned when a question is answered in the profile.
 * 
 * @author Sora
 * 
 */
public class ProfileAnswered {

    private String answeredText;
    private List<String> achievements = new ArrayList<String>();
    private List<String> pendexList = new ArrayList<String>();

    /**
     * Constructor does nothing.
     */
    public ProfileAnswered() {

    }

    public String getAnsweredText() {
        return answeredText;
    }

    public void setAnsweredText(final String answeredText) {
        this.answeredText = answeredText;
    }

    public List<String> getAchievements() {
        return achievements;
    }

    public void setAchievements(final List<String> achievements) {
        this.achievements = achievements;
    }

    public void addAchievement(final String string) {
        achievements.add(string);
    }

    public List<String> getPendexList() {
        return pendexList;
    }

    public void setPendexList(final List<String> pendexList) {
        this.pendexList = pendexList;
    }

    public void addPendexList(final String pendex) {
        pendexList.add(pendex);
    }

}
