
package com.codewar.televisionary.networkservice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import android.util.Log;

public class HttpJsonPostAndGetResponce{
	private String _responce = null;

	public void postJson(String url, String jsonString){
		HttpClient client = new DefaultHttpClient( );
		HttpConnectionParams.setConnectionTimeout(client.getParams( ), 10000); // Timeout
																			   // Limit
		HttpResponse response;
		JSONObject json = new JSONObject( );
		try{
			HttpPost post = new HttpPost(url);
			StringEntity se = new StringEntity(jsonString);
			post.setEntity(se);
			response = client.execute(post);

			if(response != null){
				String result = null;
				InputStream in = response.getEntity( ).getContent( );
				result = convertStreamToString(in);
				result = result.replace("\n", "");
				setResponce(result.toString( ));
			} else{
				Log.d("This is service response:", "This is no any service response:");
			}
		} catch(Exception e){
			e.printStackTrace( );
		}
	}

	private void setResponce(String responce){
	    this._responce = responce;
    }
	
	public String getResponce(){
		return _responce;
	}

	private static String convertStreamToString(InputStream is) throws Exception{

		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		StringBuilder sb = new StringBuilder( );
		String line = null;
		while((line = reader.readLine( )) != null){
			sb.append(line + "\n");
		}
		is.close( );
		return sb.toString( );
	}
}
