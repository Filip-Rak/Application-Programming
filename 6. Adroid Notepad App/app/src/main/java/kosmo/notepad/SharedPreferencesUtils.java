package kosmo.notepad;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class SharedPreferencesUtils
{
    // Attributes
    private static final String PREFS_NAME = "kosmo.notepad";
    private static final String NOTES_KEY = "notes";

    // Methods
    public static void saveNotes(Context context, LinkedList<Note> notes)
    {
        // Get access
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Save data data
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notes);
        editor.putString(NOTES_KEY, json);
        editor.apply();
    }

    public static LinkedList<Note> loadNotes(Context context)
    {
        // Get access
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Load notes
        Gson gson = new Gson();
        String json = sharedPreferences.getString(NOTES_KEY, null);
        Type type = new TypeToken<LinkedList<Note>>() {}.getType();
        LinkedList<Note> notes = gson.fromJson(json, type);

        // Return notes or an empty list
        return notes == null ? new LinkedList<>() : notes;
    }
}
