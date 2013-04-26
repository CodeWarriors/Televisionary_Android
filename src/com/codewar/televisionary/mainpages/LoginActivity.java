
package com.codewar.televisionary.mainpages;

import org.json.JSONException;
import org.json.JSONObject;

import com.codewar.televisionary.R;
import com.codewar.televisionary.TelevisionaryMainView;
import com.codewar.televisionary.networkservice.HttpJsonPostAndGetResponce;
import com.codewar.televisionary.parsejson.JsonParser;
import com.codewar.televisionary.sessions.SessionManager;
import com.codewar.televisionary.tasks.AlertDialogManager;
import com.codewar.televisionary.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.LoginFilter.UsernameFilterGeneric;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends Activity{

	EditText	       txtUsername, txtPassword;
	RelativeLayout	           btnLogin;
	AlertDialogManager	alert	= new AlertDialogManager( );
	SessionManager	   session;
	JSONObject	       jsonObj	= new JSONObject( );

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		session = new SessionManager(getApplicationContext( ));

		txtUsername = (EditText) findViewById(R.id.et_uname);
		txtPassword = (EditText) findViewById(R.id.et_password);
		btnLogin = (RelativeLayout) findViewById(R.id.bt_login);

		Toast.makeText(getApplicationContext( ),
		        "User Login Status : " + session.isLoggedIn( ), Toast.LENGTH_SHORT)
		        .show( );

		if(session.isLoggedIn( ) == true){
			Intent i = new Intent(getApplicationContext( ), TelevisionaryMainView.class);
			startActivity(i);
			finish( );
		}

		btnLogin.setOnClickListener(new View.OnClickListener( ){

			@Override
			public void onClick(View arg0){
				String username = txtUsername.getText( ).toString( );
				String sha1hash = Utils.getSha1Hash(txtPassword.getText( ).toString( ));

				try{
					jsonObj.put("username", username);
					jsonObj.put("password", sha1hash);
				} catch(JSONException e){
					e.printStackTrace( );
				}
				Authenticate a = new Authenticate( );
				a.execute("http://api.trakt.tv/account/settings/18cced3e20fb3673dab6f5deb5dd56be");
			}
		});
	}

	private class Authenticate extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... url){
			HttpJsonPostAndGetResponce authenticate = new HttpJsonPostAndGetResponce( );
			authenticate.postJson(url[0], jsonObj.toString( ));
			return authenticate.getResponce( );
		}

		@Override
		protected void onPostExecute(String result){
			JsonParser jParser = new JsonParser(result, 4);
			String rStatus = jParser.getProfileDetails( ).get("status").toString( );

			switch(rStatus.toLowerCase().charAt(0)){
			case 's':
				try{
					session.createLoginSession(jsonObj.getString("username").toString( ),
					        jsonObj.getString("password").toString( ));
				} catch(JSONException e){
					e.printStackTrace( );
				}
				Intent i = new Intent(getApplicationContext( ),
				        TelevisionaryMainView.class);
				startActivity(i);
				finish( );
				break;
			case 'f':
				alert.showAlertDialog(LoginActivity.this, "Login failed..",
				        "Username/Password is incorrect", false);
				break;

			default:
				break;
			}

		}

	}

}
