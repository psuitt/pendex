package pg.android.pendex.beans;


/**
 * Achievement
 * 
 * @author Sora
 * 
 */
public class Achievement {

    private String achievement;
    private String date;
    private int value = 1;

    public Achievement() {

    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(final String achievement) {
        this.achievement = achievement;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((achievement == null) ? 0 : achievement.hashCode());
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
        final Achievement other = (Achievement) obj;
        if (achievement == null) {
            if (other.achievement != null) {
                return false;
            }
        } else if (!achievement.equals(other.achievement)) {
            return false;
        }
        return true;
    }

}
