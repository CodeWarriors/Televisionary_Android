
package com.codewar.televisionary.sessions;

import java.util.HashMap;

import com.codewar.televisionary.TelevisionaryMainView;
import com.codewar.televisionary.mainpages.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager{

	SharedPreferences	        pref;
	Editor	                    editor;
	Context	                    _context;
	int	                        PRIVATE_MODE	= 0;
	private static final String	PREF_NAME	 = "TelevisionaryPref";
	private static final String	IS_LOGIN	 = "IsLoggedIn";
	public static final String	USER_NAME	 = "username";
	public static final String	HASH_KEY	 = "sha1hash";

	// constructor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit( );
	}

	// create a login session
	public void createLoginSession(String username, String sha1hash){
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(USER_NAME, username);
		editor.putString(HASH_KEY, sha1hash);
		editor.commit( );
	}

	// get stored session data
	public HashMap<String, String> getUserDetails( ){
		HashMap<String, String> user = new HashMap<String, String>( );

		user.put(USER_NAME, pref.getString(USER_NAME, null));
		user.put(HASH_KEY, pref.getString(HASH_KEY, null));

		return user;
	}

	// check login
	public void checkLogin(Context context ){

	}

	// clear session data when logout
	public void logOutUser( ){
		editor.clear( );
		editor.commit( );

		Intent i = new Intent(_context, LoginActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		_context.startActivity(i);
	}

	// Get Login State
	public boolean isLoggedIn( ){
		return pref.getBoolean(IS_LOGIN, false);
	}
}
