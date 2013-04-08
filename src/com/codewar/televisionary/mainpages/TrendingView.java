package com.codewar.televisionary.mainpages;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codewar.televisionary.tasks.DialogShow;
import com.codewar.televisionary.adapters.TrendingShowsAdapter;
import com.codewar.televisionary.jsonarrayget.HttpJsonArrayGet;
import com.codewar.televisionary.DetailViewMain;
import com.codewar.televisionary_android.R;
import com.televisionary.sourcelinks.SourceLinks;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TrendingView extends Fragment {

	ListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater
				.inflate(R.layout.trending_layout, container, false);

		SourceLinks getLink = new SourceLinks();
		getLink.setTrending_shows();
		String strUrl =getLink.getTrending_shows();

		
		
		DownloadTask downloadTask = new DownloadTask();
		downloadTask.execute(strUrl);
		

		mListView = (ListView) view.findViewById(R.id.show_list_view);
		
		
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				Intent detailsIntent = new Intent(view.getContext(), DetailViewMain.class);
				HashMap<String, String> o = (HashMap<String, String>) mListView
						.getAdapter().getItem(position);
				detailsIntent.putExtra("Show_Name", o.get("title"));
				startActivity(detailsIntent);
				
			}
		});

		return view;

	}

	private class DownloadTask extends AsyncTask<String, Integer, String> {

		String data = null;
		DialogShow loadingDialog = new DialogShow();

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			FragmentManager fragmentManager = getFragmentManager();
			loadingDialog.show(fragmentManager, "Progress Dialog");

		}

		@Override
		protected String doInBackground(String... url) {
			try {
				data = HttpJsonArrayGet.downloadFromServer(url[0]);
				Log.d("Background Task", data.toString());
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {

			loadingDialog.dismiss();

			ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();

			listViewLoaderTask.execute(result);
			Log.d("onPostExecute", result.toString());
		}
	}

	public class ListViewLoaderTask extends
			AsyncTask<String, Void, ArrayList<HashMap<String, Object>>> {

		JSONArray jArray;

		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(String... jsondata) {

			ArrayList<HashMap<String, Object>> showList = new ArrayList<HashMap<String, Object>>();
			try {
				jArray = new JSONArray(jsondata[0]);
				

				for (int i = 0; i < 50; i++) {
					JSONObject jShowObject = jArray.getJSONObject(i);
					HashMap<String, Object> Show = new HashMap<String, Object>();

					String title = jShowObject.getString("title");
					String overview = jShowObject.getString("overview");
					String air_day = jShowObject.getString("air_day");
					String air_time = jShowObject.getString("air_time");
					String network = jShowObject.getString("network");
					String Telecast_detail = air_day + " at " + air_time
							+ " On " + network;
					JSONObject jShowImages = jShowObject
							.getJSONObject("images");
					String pos_url = jShowImages.getString("poster");

					String old_imge_url = pos_url;
					Log.d("New_Image_http_url", old_imge_url);
					String[] split = old_imge_url.split(".jpg");
					String new_img_url = null;
					new_img_url = split[0] + "-138.jpg";
					Log.d("New_Image_http_url", new_img_url);
					
					Show.put("title", title);
					Show.put("overview", overview);
					Show.put("tele_detail", Telecast_detail);
					Show.put("image_url", new_img_url);


					Log.d("test", (title + overview + pos_url).toString());
					showList.add(Show);
				}

			} catch (JSONException e) {
				e.printStackTrace();
				Log.d("JSON Exception1", "Error is in Listview Loader");
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("JSON Exception1", "Error is in Listview Loader");
			}
			return showList;
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, Object>> result) {
			mListView.setAdapter(new TrendingShowsAdapter(getActivity() , R.layout.trending_list_item, result));
			
		}
		
	}

}
