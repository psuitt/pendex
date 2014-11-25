package pg.android.pendex.db.enums;


/**
 * Enums for the question json.
 * 
 * @author Sora
 * 
 */
public enum QUESTION {

    Id("id"), ParentId("parentid"), Random("random"), Question("question"), Answers("answers");

    private String name;

    QUESTION(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
