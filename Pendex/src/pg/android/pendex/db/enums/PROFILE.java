package pg.android.pendex.db.enums;

/**
 * Enums for the profile table.
 * 
 * @author Sora
 * 
 */
public enum PROFILE {

    Id("id"), Name("name"), Answers("answers"), Pendex("pendex"), Created("created"), LastAnswered(
            "lastanswered");

    private String name;

    PROFILE(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
