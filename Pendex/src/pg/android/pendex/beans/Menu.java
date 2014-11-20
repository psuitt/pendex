package pg.android.pendex.beans;

/**
 * Class for the menu.
 * 
 * @author Sora
 * 
 */
public class Menu {

    private String text;
    private MenuType type;

    public enum MenuType {
        Normal, Profile, Title;
    }

    public Menu() {
        this("", MenuType.Normal);
    }

    public Menu(final String text) {
        this(text, MenuType.Normal);
    }

    public Menu(final String text, final MenuType type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(final MenuType type) {
        this.type = type;
    }

}
