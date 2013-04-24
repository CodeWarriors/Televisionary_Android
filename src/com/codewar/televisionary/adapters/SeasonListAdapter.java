
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

public class SeasonListAdapter extends ArrayAdapter<HashMap<String, Object>>{

	private ArrayList<HashMap<String, Object>>	seasons;
	private Activity	                       activity;
	public Context	                           context;
	public ImageManager	                       imageManager;
	
	private final static String SEASON_NO = "season";
	private final static String SEASON_EPISODE_COUNT = "episodes";
	private final static String SEASON_URL = "url";
	private final static String SEASON_POSTER = "poster";



	public SeasonListAdapter(Activity seasonlistView, int textViewResourceId,
	        ArrayList<HashMap<String, Object>> seasons){
		super(seasonlistView, textViewResourceId, seasons);
		this.seasons = seasons;
		activity = seasonlistView;
		
		imageManager = new ImageManager(activity.getApplicationContext( ));
	}

	public static class ViewHolder{
		public TextView		seasonTitle;
		public TextView episode_count;
		public ImageView season_poster_image;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		ViewHolder holder;

		if(v == null){
			LayoutInflater vi = (LayoutInflater) activity
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.season_list_item, null);

			holder = new ViewHolder( );
			holder.seasonTitle = (TextView) v.findViewById(R.id.tv_seasonName);
			holder.episode_count = (TextView) v.findViewById(R.id.tv_episodeCount);
			holder.season_poster_image = (ImageView) v.findViewById(R.id.image_season_poster);
			
			
			v.setTag(holder);

		} else{
			holder = (ViewHolder) v.getTag( );
		}

		final HashMap<String, Object> season = seasons.get(position);

		if(season != null){

			holder.seasonTitle.setText( "Season " + season.get(SEASON_NO).toString( ));
			
			ImageUrlFormat iu = new ImageUrlFormat(season.get(SEASON_POSTER)
			        .toString( ), "138");
			String image_poster_url = iu.getImageUrl( );

			holder.episode_count.setText(season.get(SEASON_EPISODE_COUNT).toString( ) + " Episodes");


			holder.season_poster_image.setTag(image_poster_url);

			imageManager.displayImage(image_poster_url, holder.season_poster_image);

		}
		return v;
	}

}
