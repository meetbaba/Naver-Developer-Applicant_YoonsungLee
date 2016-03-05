package com.app.smartkidshoes;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BasePreferenceUtil {
	private static SharedPreferences mPreference;
	public static SharedPreferences instance() {
		if(mPreference == null) {
			mPreference = PreferenceManager.getDefaultSharedPreferences(ContextUtil.CONTEXT);
		}
		return mPreference;
	}
	
	public static SharedPreferences instance(Context context) {
		ContextUtil.CONTEXT = context;
		if(mPreference == null) {
			mPreference = PreferenceManager.getDefaultSharedPreferences(context);
		}
		return mPreference;
	}
	
	public static void put(String key, String value) {
		SharedPreferences sp = instance();
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String get(String key) {
		SharedPreferences sp = instance();
		return sp.getString(key, null);
	}
	
	public static String getWithNullToBlank(String key) {
		SharedPreferences sp = instance();
		return sp.getString(key, "");
	}
	
	public static void put(String key, boolean value) {
		SharedPreferences sp = instance();
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static boolean get(String key, boolean value) {
		SharedPreferences sp = instance();
		return sp.getBoolean(key, value);
	}
	
	public static void put(String key, int value) {
		SharedPreferences sp = instance();
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static int get(String key, int value) {
		SharedPreferences sp = instance();
		return sp.getInt(key, value);
	}
	
	public static void put(String key, Set<String> set) {
		SharedPreferences sp = instance();
		SharedPreferences.Editor editor = sp.edit();
		editor.putStringSet(key, set);
		editor.commit();
	}
	
	public static Set<String> getStringSet(String key) {
		SharedPreferences sp = instance();
		return sp.getStringSet(key, null);
	}
}
