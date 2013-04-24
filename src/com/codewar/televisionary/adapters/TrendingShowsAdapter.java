
package com.codewar.televisionary.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codewar.televisionary.R;
import com.codewar.televisionary.sourcelinks.ImageUrlFormat;
import com.codewar.televisionary.tasks.ImageManager;

public class TrendingShowsAdapter extends ArrayAdapter<HashMap<String, Object>>{

	private ArrayList<HashMap<String, Object>>	shows;
	private Activity	                       activity;
	public Context	                           context;
	public ImageManager	                       imageManager;

	private final static String	               SHOW_TITLE	           = "title";
	private final static String	               SHOW_YEAR	           = "year";
	private final static String	               SHOW_URL	               = "url";
	private final static String	               SHOW_FIRST_AIRED	       = "first_aired";
	private final static String	               SHOW_COUNTRY	           = "country";
	private final static String	               SHOW_OVERVIEW	       = "overview";
	private final static String	               SHOW_RUNTIME	           = "runtime";
	private final static String	               SHOW_NETWORK	           = "network";
	private final static String	               SHOW_AIR_DAY	           = "air_day";
	private final static String	               SHOW_AIR_TIME	       = "air_time";
	private final static String	               SHOW_CERTIFICATION	   = "certification";
	private final static String	               SHOW_IMDB_ID	           = "imdb_id";
	private final static String	               SHOW_TVDB_ID	           = "tvdb_id";
	private final static String	               SHOW_TVRAGE_ID	       = "tvrage_id";
	private final static String	               SHOW_IMAGES_POSTER	   = "poster";
	private final static String	               SHOW_IMAGES_FANART	   = "fanart";
	private final static String	               SHOW_IMAGES_BANNER	   = "banner";
	private final static String	               SHOW_WATCHERS	       = "watchers";
	private final static String	               SHOW_RATINGS_PERCENTAGE	= "percentage";
	private final static String	               SHOW_RATINGS_VOTES	   = "votes";
	private final static String	               SHOW_RATINGS_LOVED	   = "loved";
	private final static String	               SHOW_RATINGS_HATED	   = "hated";
	private final static String	               SHOW_GENRES	           = "genres";

	private final static String	               SHOW_RATINGS	           = "ratings";
	private final static String	               SHOW_IMAGES	           = "images";

	public TrendingShowsAdapter(Activity trendingView, int textViewResourceId,
	        ArrayList<HashMap<String, Object>> shows){
		super(trendingView, textViewResourceId, shows);
		this.shows = shows;
		activity = trendingView;
		imageManager = new ImageManager(activity.getApplicationContext( ));
	}

	public static class ViewHolder{

		public TextView		showTitle;
		public TextView		showDetails;
		public TextView		airedDetails;
		public ImageView	posterImage;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		ViewHolder holder;

		if(v == null){
			LayoutInflater vi = (LayoutInflater) activity
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.trending_list_item, null);

			holder = new ViewHolder( );
			holder.showTitle = (TextView) v.findViewById(R.id.show_title_1);
			holder.showDetails = (TextView) v.findViewById(R.id.show_summary_1);
			holder.airedDetails = (TextView) v.findViewById(R.id.show_aired_details_1);
			holder.posterImage = (ImageView) v.findViewById(R.id.image_pos);

			v.setTag(holder);

		} else{
			holder = (ViewHolder) v.getTag( );
		}

		final HashMap<String, Object> show = shows.get(position);

		if(show != null){

			ImageUrlFormat iu = new ImageUrlFormat(show.get(SHOW_IMAGES_POSTER)
			        .toString( ), "138");
			String image_poster_url = iu.getImageUrl( );
			String aired_details = show.get(SHOW_AIR_DAY).toString( )+" at "+show.get(SHOW_AIR_TIME).toString( )+" On "+show.get(SHOW_NETWORK);
			holder.showTitle.setText(show.get(SHOW_TITLE).toString( ));
			holder.showDetails.setText(show.get(SHOW_OVERVIEW).toString( ));
			holder.airedDetails.setText(aired_details);
			holder.posterImage.setTag(image_poster_url);

			imageManager.displayImage(image_poster_url, holder.posterImage);
		}
		return v;
	}

}
