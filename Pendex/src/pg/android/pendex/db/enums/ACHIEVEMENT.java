package pg.android.pendex.db.enums;

/**
 * Enums for the answer json.
 * 
 * @author Sora
 * 
 */
public enum ACHIEVEMENT {

    Achievement("achievement"), Date("date"), Value("value");

    private String name;

    ACHIEVEMENT(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
