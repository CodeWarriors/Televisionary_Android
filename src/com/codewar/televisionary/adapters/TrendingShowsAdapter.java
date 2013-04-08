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
import com.codewar.televisionary.tasks.ImageManager;

public class TrendingShowsAdapter extends ArrayAdapter<HashMap<String, Object>> {

	private  ArrayList<HashMap<String, Object>> shows;
	private Activity activity;
	public Context context;
	public ImageManager imageManager;
	
	
	public TrendingShowsAdapter(Activity trendingView, int textViewResourceId,
			ArrayList<HashMap<String, Object>> shows) {
		super(trendingView, textViewResourceId , shows);
		this.shows = shows;
		activity = trendingView;
		imageManager  = new  ImageManager(activity.getApplicationContext());
	}
	
	public static class ViewHolder{
		public TextView showTitle;
		public TextView showDetails;
		public TextView airedDetails;
		public ImageView posterImage;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		
		if(v == null){
			LayoutInflater vi = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.trending_list_item, null);
			
			holder = new ViewHolder();
			holder.showTitle = (TextView)v.findViewById(R.id.show_title_1);
			holder.showDetails =(TextView)v.findViewById(R.id.show_summary_1);
			holder.airedDetails = (TextView)v.findViewById(R.id.show_aired_details_1);
			holder.posterImage =  (ImageView)v.findViewById(R.id.image_pos);
			
			v.setTag(holder);
		
		}else{
			holder=(ViewHolder)v.getTag();
		}
		
		final HashMap<String,Object> show = shows.get(position);

		if(show!=null){
			holder.showTitle.setText(show.get("title").toString());
			holder.showDetails.setText(show.get("overview").toString());
			holder.airedDetails.setText(show.get("tele_detail").toString());
			holder.posterImage.setTag(show.get("image_url").toString());
			
			imageManager.displayImage(show.get("image_url").toString(), holder.posterImage);
		}
		return v;
	}

}
