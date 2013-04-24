
package com.codewar.televisionary.mainpages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.codewar.televisionary.DetailViewMain;
import com.codewar.televisionary.R;
import com.codewar.televisionary.adapters.TrendingShowsAdapter;
import com.codewar.televisionary.jsonarrayget.HttpJsonArrayGet;
import com.codewar.televisionary.parsejson.JsonParser;
import com.codewar.televisionary.sourcelinks.SourceLinks;
import com.codewar.televisionary.tasks.DialogShow;
import com.codewar.televisionary.tasks.LoadFromCache;
import com.codewar.televisionary.tasks.NetworkTestTask;
import com.codewar.televisionary.tasks.ShowAlertDialog;

public class TrendingView extends Fragment{

	ListView	                       mListView;
	NetworkTestTask	                   netTest;
	ShowAlertDialog	                   alert;
	boolean	                           isSdAvail	 = true;
	boolean	                           isSdWriteable	= true;

	ArrayList<HashMap<String, Object>>	show_list	 = null;

	@Override
	public void onActivityCreated(Bundle savedInstanceState){

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState){
		final View view = inflater.inflate(R.layout.trending_layout, container, false);

		NetworkTestTask netTest = new NetworkTestTask(getActivity( )
		        .getApplicationContext( ));
		if(netTest.isNetworkAvailable( ) == true){
			SourceLinks getLink = new SourceLinks( );
			getLink.setTrending_shows( );
			String strUrl = getLink.getTrending_shows( );
			DownloadTask downloadTask = new DownloadTask( );
			
			downloadTask.execute(strUrl);
			//for testing
			//downloadTask.execute("http://192.248.12.9/~114064N/files/shows.json");
		} else{
			Toast.makeText(getActivity( ).getApplicationContext( ),
			        "Network is Not Available.. Work in Offline Mode", 15000).show( );
			LoadFromCache loadData = new LoadFromCache("tendinglist");
			Toast.makeText(getActivity( ).getApplicationContext( ),
			        loadData.getOfflineJson( ), 15000).show( );
			OfflineTask offlineTask = new OfflineTask( );
			offlineTask.execute(loadData.getOfflineJson( ));
		}

		mListView = (ListView) view.findViewById(R.id.show_list_view);
		mListView.setOnItemClickListener(new OnItemClickListener( ){

			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id){
				Intent detailsIntent = new Intent(view.getContext( ),
				        DetailViewMain.class);

				final HashMap<String, Object> show = show_list.get(position);

				detailsIntent.putExtra("show_summary", show);
				startActivity(detailsIntent);

			}
		});

		return view;

	}

	private class DownloadTask extends
	        AsyncTask<String, Integer, ArrayList<HashMap<String, Object>>>{

		String		data		  = null;
		DialogShow	loadingDialog	= new DialogShow( );

		@Override
		protected void onPreExecute( ){
			super.onPreExecute( );
			FragmentManager fragmentManager = getFragmentManager( );
			loadingDialog.show(fragmentManager, "Progress Dialog");
			Log.d("Background Task", "OnPre Execute");
		}

		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(String... url){

			try{
				data = HttpJsonArrayGet.downloadFromServer(url[0]);
				Log.d("Background Task", url[0].toString( ));
				Log.d("Background Task", data.toString( ));
			} catch(Exception e){
				Log.d("Background Task", e.toString( ));
			}

			JsonParser jParser = new JsonParser(data.toString( ), 1);
			return jParser.getTrendingListData( );
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, Object>> result){

			loadingDialog.dismiss( );
			mListView.setAdapter(new TrendingShowsAdapter(getActivity( ),
			        R.layout.trending_list_item, result));

			show_list = result;

		}
	}

	private class OfflineTask extends
	        AsyncTask<String, Integer, ArrayList<HashMap<String, Object>>>{

		String		data		  = null;

		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(String... url){

			JsonParser jParser = new JsonParser(url[0], 1);
			return jParser.getTrendingListData( );
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, Object>> result){
			mListView.setAdapter(new TrendingShowsAdapter(getActivity( ),
			        R.layout.trending_list_item, result));

			show_list = result;

		}
	}

}
