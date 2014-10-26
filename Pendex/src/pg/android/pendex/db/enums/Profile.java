package pg.android.pendex.db.enums;

/**
 * Enums for the profile table.
 * @author Sora
 *
 */
public enum Profile {

	Id("id"),
	Name("name"),
	Answers("answers"),
	Pendex("pendex");

	public String name;

	Profile(final String name) {
		this.name = name;
	}

}
