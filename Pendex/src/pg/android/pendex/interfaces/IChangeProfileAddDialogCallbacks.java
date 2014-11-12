package pg.android.pendex.interfaces;

/**
 * Change profile add dialog call back.
 * 
 * @author Sora
 * 
 */
public interface IChangeProfileAddDialogCallbacks {

    /**
     * This is called after a profile has been created.
     * 
     * @param newUserId - String - new users id.
     */
    void createdUser(final String newUserId);

}
