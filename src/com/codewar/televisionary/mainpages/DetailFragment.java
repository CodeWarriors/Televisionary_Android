
package com.codewar.televisionary.mainpages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.codewar.televisionary.R;
import com.codewar.televisionary.jsonarrayget.HttpJsonArrayGet;
import com.codewar.televisionary.parsejson.JsonParser;
import com.codewar.televisionary.sourcelinks.ImageUrlFormat;
import com.codewar.televisionary.sourcelinks.SourceLinks;
import com.codewar.televisionary.tasks.DialogShow;
import com.codewar.televisionary.tasks.SingleImageLoader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailFragment extends Fragment{

	TextView	                show_title;
	TextView	                aired_time;
	TextView	                airdayNnet;
	TextView	                overview_txt;
	TextView	                rating_percentage_txt;
	TextView	                rating_loved_txt;
	TextView	                rating_hated_txt;

	ImageView	                poster;
	ImageView	                banner;
	RatingBar	                rating;

	private final static String	SHOW_TITLE	            = "title";
	private final static String	SHOW_YEAR	            = "year";
	private final static String	SHOW_URL	            = "url";
	private final static String	SHOW_FIRST_AIRED	    = "first_aired";
	private final static String	SHOW_COUNTRY	        = "country";
	private final static String	SHOW_OVERVIEW	        = "overview";
	private final static String	SHOW_RUNTIME	        = "runtime";
	private final static String	SHOW_NETWORK	        = "network";
	private final static String	SHOW_AIR_DAY	        = "air_day";
	private final static String	SHOW_AIR_TIME	        = "air_time";
	private final static String	SHOW_CERTIFICATION	    = "certification";
	private final static String	SHOW_IMDB_ID	        = "imdb_id";
	private final static String	SHOW_TVDB_ID	        = "tvdb_id";
	private final static String	SHOW_TVRAGE_ID	        = "tvrage_id";
	private final static String	SHOW_IMAGES_POSTER	    = "poster";
	private final static String	SHOW_IMAGES_FANART	    = "fanart";
	private final static String	SHOW_IMAGES_BANNER	    = "banner";
	private final static String	SHOW_WATCHERS	        = "watchers";
	private final static String	SHOW_RATINGS_PERCENTAGE	= "percentage";
	private final static String	SHOW_RATINGS_VOTES	    = "votes";
	private final static String	SHOW_RATINGS_LOVED	    = "loved";
	private final static String	SHOW_RATINGS_HATED	    = "hated";
	private final static String	SHOW_GENRES	            = "genres";
	private final static String	TVDB_ID	                = "tvdb_id";

	private final static String	SHOW_RATINGS	        = "ratings";
	private final static String	SHOW_IMAGES	            = "images";

	String	                    title_text;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState){
		final View view = inflater
		        .inflate(R.layout.detailview_summaary, container, false);

		// Referenceing
		poster = (ImageView) view.findViewById(R.id.poster_image_detailview);
		banner = (ImageView) view.findViewById(R.id.im_banner);
		show_title = (TextView) view.findViewById(R.id.title_txt_detailview);
		aired_time = (TextView) view.findViewById(R.id.tv_airtime);
		airdayNnet = (TextView) view.findViewById(R.id.tv_airdayNet);
		overview_txt = (TextView) view.findViewById(R.id.show_summary_txt_detailview);
		rating_percentage_txt = (TextView) view
		        .findViewById(R.id.rating_percentage_detailview);
		rating_loved_txt = (TextView) view.findViewById(R.id.loved_txt_detailview);
		rating_hated_txt = (TextView) view.findViewById(R.id.hated_txt_detailview);
		rating = (RatingBar) view.findViewById(R.id.bar_detailview);

		// get data fro previous activity
		Intent intent = getActivity( ).getIntent( );
		HashMap<String, Object> show_summary = (HashMap<String, Object>) intent
		        .getSerializableExtra("show_summary");
		System.out.println(show_summary.toString( ));

		String show_aired_details = show_summary.get(SHOW_AIR_DAY).toString( ) + " On "
		        + show_summary.get(SHOW_NETWORK);
		String rating_prog_str = show_summary.get(SHOW_RATINGS_PERCENTAGE).toString( );
		int rating_prog_int = Integer.parseInt(rating_prog_str);

		show_title.setText(show_summary.get(SHOW_TITLE).toString( ));
		aired_time.setText(show_summary.get(SHOW_AIR_TIME).toString( ).toUpperCase( ));
		airdayNnet.setText(show_aired_details);
		overview_txt.setText(show_summary.get(SHOW_OVERVIEW).toString( ));
		rating_percentage_txt.setText(show_summary.get(SHOW_RATINGS_PERCENTAGE)
		        .toString( ) + "%");
		rating_loved_txt.setText(show_summary.get(SHOW_RATINGS_LOVED).toString( ));
		rating_hated_txt.setText(show_summary.get(SHOW_RATINGS_HATED).toString( ));
		rating.setProgress(rating_prog_int);

		DownloadTask downloadTask = new DownloadTask( );
		downloadTask.execute(show_summary.get(SHOW_IMAGES_POSTER).toString( ));

		LoadBanner loadBanner = new LoadBanner( );
		loadBanner.execute(show_summary.get(SHOW_IMAGES_BANNER).toString( ));

		return view;

	}

	private class DownloadTask extends AsyncTask<String, Integer, Bitmap>{

		DialogShow	loadingDialog	= new DialogShow( );

		@Override
		protected void onPreExecute( ){
			FragmentManager fragmentManager = getFragmentManager( );
			loadingDialog.show(fragmentManager, "Progress Dialog");
		}

		@Override
		protected Bitmap doInBackground(String... url){

			ArrayList<Bitmap> aa = new ArrayList<Bitmap>( );
			ImageUrlFormat poster_url_format = new ImageUrlFormat(url[0], "300");
			SingleImageLoader imageLoader = new SingleImageLoader(getActivity( )
			        .getApplicationContext( ));
			Bitmap poster_bmp = imageLoader.displaySingleImage(
			        poster_url_format.getImageUrl( ), poster);
			return poster_bmp;
		}

		@Override
		protected void onPostExecute(Bitmap result){

			loadingDialog.dismiss( );
			poster.setImageBitmap(result);

			View detail_layout = (View) getView( ).findViewById(
			        R.id.detailview_summary_back);

			Drawable dr_image = new BitmapDrawable(result);
			detail_layout.setBackgroundDrawable(dr_image);

			Drawable background = detail_layout.getBackground( );

			background.setAlpha(20);
		}

	}

	private class LoadBanner extends AsyncTask<String, Integer, Bitmap>{

		DialogShow	loadingDialog	= new DialogShow( );

		@Override
		protected void onPreExecute( ){
			FragmentManager fragmentManager = getFragmentManager( );
			loadingDialog.show(fragmentManager, "Progress Dialog");
		}

		@Override
		protected Bitmap doInBackground(String... url){

			SingleImageLoader imageLoader = new SingleImageLoader(getActivity( )
			        .getApplicationContext( ));

			Bitmap fanart_bmp = imageLoader.displaySingleImage(url[0], poster);

			return fanart_bmp;
		}

		@Override
		protected void onPostExecute(Bitmap result){

			loadingDialog.dismiss( );
			banner.setImageBitmap(result);
		}

	}

}
