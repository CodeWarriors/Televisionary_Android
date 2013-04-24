
package com.codewar.televisionary.mainpages;

import java.util.ArrayList;
import java.util.HashMap;

import com.codewar.televisionary.DetailViewMain;
import com.codewar.televisionary.R;
import com.codewar.televisionary.adapters.SeasonListAdapter;
import com.codewar.televisionary.adapters.TrendingShowsAdapter;
import com.codewar.televisionary.jsonarrayget.HttpJsonArrayGet;
import com.codewar.televisionary.parsejson.JsonParser;
import com.codewar.televisionary.sourcelinks.SourceLinks;
import com.codewar.televisionary.tasks.DialogShow;
import com.codewar.televisionary.tasks.LoadFromCache;
import com.codewar.televisionary.tasks.NetworkTestTask;
import com.codewar.televisionary.tasks.ShowAlertDialog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SeasonsFragment extends Fragment{

	ListView	                       seasonListView;
	NetworkTestTask	                   netTest;
	ShowAlertDialog	                   alert;
	boolean	                           isSdAvail	 = true;
	boolean	                           isSdWriteable	= true;

	ArrayList<HashMap<String, Object>>	season_list	 = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState){
		final View view = inflater.inflate(R.layout.detailview_seasonlist, container,
		        false);

		// get data fro previous activity
		Intent intent = getActivity( ).getIntent( );
		HashMap<String, Object> show_summary = (HashMap<String, Object>) intent
		        .getSerializableExtra("show_summary");
		String showName = show_summary.get("tvdb_id").toString( );
		
		Log.i("Received show name", showName);

		NetworkTestTask netTest = new NetworkTestTask(getActivity( )
		        .getApplicationContext( ));
		if(netTest.isNetworkAvailable( ) == true){
			SourceLinks getLink = new SourceLinks( );
			getLink.setShow_seasons(showName);
			String strUrl = getLink.getShow_seasons( );
			
			Log.i("Received showtitle", showName);

			DownloadTask downloadTask = new DownloadTask( );
			downloadTask.execute(strUrl);
			//testing link
			//downloadTask.execute("http://192.248.12.9/~114064N/files/seasonlist.json");
		} else{
			Toast.makeText(getActivity( ).getApplicationContext( ),
			        "Network is Not Available.. Work in Offline Mode", 15000).show( );
			LoadFromCache loadData = new LoadFromCache( "seasonlist");
			Toast.makeText(getActivity( ).getApplicationContext( ),
			        loadData.getOfflineJson( ), 15000).show( );
			OfflineTask offlineTask = new OfflineTask( );
			offlineTask.execute(loadData.getOfflineJson( ));
		}

		seasonListView = (ListView) view.findViewById(R.id.list_seasonlist);


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

			JsonParser jParser = new JsonParser(data.toString( ), 3);
			Log.d("Parced Json",jParser.getSeasonData( ).toString( ));
			return jParser.getSeasonData( );
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, Object>> result){

			loadingDialog.dismiss( );
			Log.d("onPost execute", result.toString( ));
			seasonListView.setAdapter(new SeasonListAdapter(getActivity( ),
			        R.layout.season_list_item, result));

			season_list = result;

		}
	}

	private class OfflineTask extends
	        AsyncTask<String, Integer, ArrayList<HashMap<String, Object>>>{

		String	data	= null;

		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(String... url){

			JsonParser jParser = new JsonParser(url[0], 1);
			return jParser.getTrendingListData( );
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, Object>> result){
			seasonListView.setAdapter(new SeasonListAdapter(getActivity( ),
			        R.layout.season_list_item, result));

			season_list = result;

		}
	}
}
