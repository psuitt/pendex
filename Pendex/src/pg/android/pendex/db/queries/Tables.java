package pg.android.pendex.db.queries;

import pg.android.pendex.db.enums.Profile;

public class Tables {

	public static final String CreateProfileTable =
			"CREATE TABLE "
					+ Profile.class.getSimpleName()
					+ " ( "
					+ Profile.ID + " " + Profile.ID.type + ", "
					+ Profile.ID + " " + Profile.Name.type
					+ " )";

}
