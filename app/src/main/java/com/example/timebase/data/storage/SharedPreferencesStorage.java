package com.example.timebase.data.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import static android.content.Context.MODE_PRIVATE;

import com.example.timebase.common.Constans;


public class SharedPreferencesStorage {
    private final SharedPreferences pref;
    private final Editor editor;
    private static SharedPreferencesStorage instance;

    @SuppressLint("CommitPrefEdits")
    private SharedPreferencesStorage(Context context) {
        pref = context.getApplicationContext().getSharedPreferences(Constans.NAME_PREF_STORAGE, MODE_PRIVATE);
        editor = pref.edit();
    }

    public static synchronized SharedPreferencesStorage getInstance(Context... context){
        if(instance == null){
            if(context[0] != null)instance = new SharedPreferencesStorage(context[0]);
        }
        return instance;
    }

    public void saveDataString(String key,String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void saveDataBoolean(String key,boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void saveDataLong(String key, long value){
        editor.putLong(key, value);
        editor.commit();
    }

    public Boolean loadDataBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    public String loadDataString(String key) {
        return pref.getString(key, "");
    }

    public Long loadDataLong(String key) {
        return pref.getLong(key, -1);
    }

    public void clearAll() {
        pref.edit().clear().apply();
    }
}
