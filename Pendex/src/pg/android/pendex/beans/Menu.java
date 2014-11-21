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
    private int icon;

    public enum MenuType {
        Normal, Profile, Title;
    }

    public Menu() {
        this("");
    }

    public Menu(final String text) {
        this(text, MenuType.Normal, 0);
    }

    public Menu(final String text, final MenuType type) {
        this(text, type, 0);
    }

    public Menu(final String text, final MenuType type, final int icon) {
        this.text = text;
        this.type = type;
        this.icon = icon;
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(final int icon) {
        this.icon = icon;
    }

}
