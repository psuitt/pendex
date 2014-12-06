package pg.android.pendex.db.enums;

/**
 * Like map.
 * 
 * @author Sora
 *
 */
public enum LIKE {
    
    Like("like"), 
    LikeMap("likemap");

    private String name;

    LIKE(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
