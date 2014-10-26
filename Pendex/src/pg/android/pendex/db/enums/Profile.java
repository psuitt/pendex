package pg.android.pendex.db.enums;

/**
 * Enums for the profile table.
 * @author Sora
 *
 */
public enum Profile {

	ID("INTEGER PRIMARY KEY AUTOINCREMENT"),
	Name("TEXT");

	public String type;

	Profile(final String type) {
		this.type = type;
	}

}
