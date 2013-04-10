package com.codewar.televisionary;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codewar.televisionary.jsonarrayget.HttpJsonArrayGet;
import com.codewar.televisionary.sourcelinks.SourceLinks;
import com.codewar.televisionary.tasks.ImageManager;
import com.codewar.televisionary.tasks.SingleImageLoader;

public class DetailViewMain extends Activity {
	
	TextView passedtext;
	ImageView Im;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		
		super.onCreate(arg0);
		setContentView(R.layout.detail_main_view);
		//getting clicked show name from trending shows Activity
		Intent intent = getIntent();
		String title_txt = intent.getStringExtra("Show_Name");
		//String back_image_url = intent.getStringExtra("Back_Image");
		
		passedtext =(TextView)findViewById(R.id.title_txt_detailview);
		passedtext.setText(title_txt);
		
			//setWallpaper(im.getBitmap(back_image_url));
	
		
		//setting the show details source link
		SourceLinks getLink = new SourceLinks();	
		getLink.setShow_details(title_txt);	
		
		//get the link and download the json data from link
		DownloadTask downloadTask = new DownloadTask();	
		downloadTask.execute(getLink.getShow_details());	
		
		
	}
	

	//download the json data from given link
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
				Log.i("Background Task Detail main View", data.toString());
			} catch (Exception e) {
				Log.d("Background Task Detail main view", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			DetailsLoaderTask detailsLoaderTask = new DetailsLoaderTask();
			detailsLoaderTask.execute(result);
		}

	}

	//parse json data from downloaded json data
	private class DetailsLoaderTask extends AsyncTask<String, Void, ArrayList<String>> {
		JSONObject jDetailObject;

		@Override
		protected ArrayList<String> doInBackground(String... jsondata) {

			ArrayList<String> details = new ArrayList<String>();
			
			try {
				jDetailObject = new JSONObject(jsondata[0]);
				
				String title = jDetailObject.getString("title");
				String overview = jDetailObject.getString("overview");
				String air_day = jDetailObject.getString("air_day");
				String air_time = jDetailObject.getString("air_time");
				String network = jDetailObject.getString("network");
				

				JSONObject jShowImages = jDetailObject.getJSONObject("images");
				String pos_url = jShowImages.getString("poster");
				String fanart_url = jShowImages.getString("fanart");
				String banner_url = jShowImages.getString("banner");
				
				JSONObject jRaitng = jDetailObject.getJSONObject("ratings");
				String rating_percentage = jRaitng.getString("percentage");
				String reting_loved = jRaitng.getString("loved");

				details.add(title);
				details.add(overview);
				details.add(air_day);
				details.add(air_time);
				details.add(network);
				details.add(pos_url);
				details.add(fanart_url);
				details.add(banner_url);
				details.add(rating_percentage);
				details.add(reting_loved);
				
			} catch (JSONException e) {
				e.printStackTrace();
				Log.d("JSON Exception1", "Error is in Listview Loader");
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("JSON Exception1", "Error is in Listview Loader");
			}
			
			return details;
		}
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			
			String title = result.get(0).toString();
			String overview =  result.get(1).toString();
			String air_day =  result.get(2).toString();
			String air_time =  result.get(3).toString();
			String network =  result.get(4).toString();
			String pos_url =  result.get(5).toString();;
			String fanart_url =  result.get(6).toString();
			String banner_url = result.get(7).toString();
			String rating_percentage =  result.get(8).toString();
			String rating_loved =  result.get(9).toString();
			

			Log.d("received Details", title);
			Log.d("received Details", air_day);
			Log.d("received Details", air_time);
			Log.d("received Details", network);
			Log.d("received Details", rating_percentage);
			Log.d("received Details", rating_loved);
			


			ImageView poster = (ImageView)findViewById(R.id.poster_image_detailview);
			TextView aired_details_txt = (TextView)findViewById(R.id.aired_details_detailview);
			TextView overview_txt = (TextView)findViewById(R.id.show_summary_txt_detailview);
			TextView rating_percentage_txt = (TextView)findViewById(R.id.rating_percentage_detailview);
			TextView rating_loved_txt = (TextView)findViewById(R.id.loved_txt_detailview);
			//RatingBar rating = (RatingBar)findViewById(R.id.bar_detailview);
			
				
			String[] splitStr = fanart_url.split(".jpg");
			String new_fanart_url = splitStr[0]+"-940.jpg";
			
			String[] posterSplitStr = pos_url.split(".jpg");
			String new_poster_url = posterSplitStr[0]+"-138.jpg";
			
			SingleImageLoader aa = new SingleImageLoader(getApplicationContext());

			poster.setImageBitmap(aa.displaySingleImage(new_poster_url, poster));
			aired_details_txt.setText(air_day+" at "+air_time+" On "+network);
			overview_txt.setText(overview);
			rating_percentage_txt.setText(rating_percentage+"%");
			rating_loved_txt.setText(rating_loved);
			
		
			Bitmap back_img =aa.displaySingleImage(new_fanart_url, poster);
			View detail_layout =(View)findViewById(R.id.aa);
			
			Drawable dr_image = new BitmapDrawable(back_img);
			detail_layout.setBackgroundDrawable(dr_image);
			
			Drawable background = detail_layout.getBackground();
			background.setAlpha(50);
			

		}
	}
}
