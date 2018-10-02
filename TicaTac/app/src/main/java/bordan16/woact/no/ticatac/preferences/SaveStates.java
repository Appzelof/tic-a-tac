package bordan16.woact.no.ticatac.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by daniel on 08/03/2018.
 */

public class SaveStates {

    private static final String PREF_FILE_NAME = "SaveFile";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SaveStates(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();

    }

    public void saveString(String key, String value){
        editor.putString(key,value);
        editor.commit();

    }

    public String load(String key){
      String loadSave = preferences.getString(key, "");

      return loadSave;
    }

    public void saveInt(String key, int value){
        editor.putInt(key, value);
        editor.commit();
    }

    public int loadInt(String key){
        int loadSave = preferences.getInt(key, 0);

        return loadSave;
    }

}
