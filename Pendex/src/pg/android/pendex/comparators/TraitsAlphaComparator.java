package pg.android.pendex.comparators;

import java.util.Comparator;

import pg.android.pendex.beans.Trait;

public class TraitsAlphaComparator implements Comparator<Trait> {

	/**
	 * Sorts traits alphabetical.
	 */
	@Override
	public int compare(final Trait lhs, final Trait rhs) {
		return lhs.getTrait().compareToIgnoreCase(rhs.getTrait());
	}

}
