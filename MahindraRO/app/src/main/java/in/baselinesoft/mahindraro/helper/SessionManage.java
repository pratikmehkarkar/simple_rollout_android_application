package in.baselinesoft.mahindraro.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Pratik on 16/03/2018.
 */

public class SessionManage
{

    private static String TAG = SessionManage.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref1;
    SharedPreferences.Editor editor1;
    Context _context1;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "baseline";

    private static final String KEY_IS_LOGGEDIN = "isLogged";

    public SessionManage(Context context)
    {
        this._context1 = context;
        pref1 = _context1.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor1 = pref1.edit();
    }

    public void setLogin(boolean isLoggedIn)
    {
        editor1.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor1.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLogged()
    {
        return pref1.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}