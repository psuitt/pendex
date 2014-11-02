package pg.android.pendex.beans;

/**
 * Traits.
 * 
 * @author Sora
 * 
 */
public class Trait {

	private String trait;
	private int traitValue;
	private String summary;

	public Trait() {

	}

	public String getTrait() {
		return trait;
	}

	public void setTrait(final String trait) {
		this.trait = trait;
	}

	public int getTraitValue() {
		return traitValue;
	}

	public void setTraitValue(final int traitValue) {
		this.traitValue = traitValue;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

}
