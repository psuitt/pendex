package pg.android.pendex.db.enums;

/**
 * Enums for the answer json.
 * 
 * @author Sora
 * 
 */
public enum ANSWER {

    Answer("answer"), Pendex("pendex"), Achievement("achievement"), LinkedType("linkedtype"), Linked(
            "linked");

    private String name;

    ANSWER(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
