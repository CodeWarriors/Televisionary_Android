package com.codewar.televisionary_android;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codewar.televisionary.jsonarrayget.HttpJsonArrayGet;
import com.televisionary.sourcelinks.SourceLinks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.TextView;

public class DetailViewMain extends Activity {
	TextView passedtext;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.detail_main_view);

		Intent intent = getIntent();
		String txt = intent.getStringExtra("Show_Name");
		passedtext = (TextView) findViewById(R.id.txt);
		passedtext.setText(txt);
		
		SourceLinks getLink = new SourceLinks();
	
		DownloadTask downloadTask = new DownloadTask();
		
		getLink.setShow_details(txt);
		
		downloadTask.execute(getLink.getShow_details());

	}

	private class DownloadTask extends AsyncTask<String, Integer, String> {

		String data = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... url) {
			try {
				data = HttpJsonArrayGet.downloadFromServer(url[0]);
				Log.i("Background Task", data.toString());
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			DetailsLoaderTask detailsLoaderTask = new DetailsLoaderTask();
			detailsLoaderTask.execute(result);
		}

	}

	private class DetailsLoaderTask extends AsyncTask<String, Integer, String> {

		JSONObject jDetailObject;

		@Override
		protected String doInBackground(String... jsondata) {

			try {
				jDetailObject = new JSONObject(jsondata[0]);
				HashMap<String, Object> Show = new HashMap<String, Object>();

				String title = jDetailObject.getString("title");
				String overview = jDetailObject.getString("overview");
				String air_day = jDetailObject.getString("air_day");
				String air_time = jDetailObject.getString("air_time");
				String network = jDetailObject.getString("network");
				

				JSONObject jShowImages = jDetailObject.getJSONObject("images");
				String pos_url = jShowImages.getString("poster");
				String fanart_url = jShowImages.getString("fanart");
				
				JSONObject jRaitng = jDetailObject.getJSONObject("ratings");
				String rating_percentage = jRaitng.getString("percentage");
				String reting_loved = jRaitng.getString("loved");
				
				Log.d("DETAIL VIEW", title);
				Log.d("DETAIL VIEW", overview);
				Log.d("DETAIL VIEW", air_day);
				Log.d("DETAIL VIEW", air_time);
				Log.d("DETAIL VIEW", network);
				Log.d("DETAIL VIEW", pos_url);
				Log.d("DETAIL VIEW", fanart_url);


			} catch (JSONException e) {
				e.printStackTrace();
				Log.d("JSON Exception1", "Error is in Listview Loader");
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("JSON Exception1", "Error is in Listview Loader");
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}
	}
}
