package com.codewar.televisionary.mainpages;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.televisionary.tasks.DialogShow;
import com.codewar.televisionary.jsonarrayget.HttpJsonArrayGet;
import com.codewar.televisionary_android.DetailViewMain;
import com.codewar.televisionary_android.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.televisionary.sourcelinks.SourceLinks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

	private class ListViewLoaderTask extends
			AsyncTask<String, Void, SimpleAdapter> {

		JSONArray jArray;

		@Override
		protected SimpleAdapter doInBackground(String... jsondata) {

			List<HashMap<String, Object>> showList = new ArrayList<HashMap<String, Object>>();
			try {
				jArray = new JSONArray(jsondata[0]);
				int ObjectCount = jArray.length();

				for (int i = 0; i < 6; i++) {
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

					Show.put("title", title);
					Show.put("overview", overview);
					Show.put("tele_detail", Telecast_detail);
					Show.put("image_url", pos_url);
					Show.put("Image_path", R.drawable.ic_trakt);

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

			String[] from = { "title", "overview", "tele_detail", "image_path" };

			int[] to = { R.id.textView1, R.id.textView2, R.id.textView3,
					R.id.image_pos };

			SimpleAdapter adapter = new SimpleAdapter(getActivity()
					.getBaseContext(), showList, R.layout.epirow, from, to);
			return adapter;
		}

		
		@Override
		protected void onPostExecute(SimpleAdapter adapter) {
			mListView.setAdapter(adapter);

			for (int i = 0; i < adapter.getCount(); i++) {
				HashMap<String, Object> hm = (HashMap<String, Object>) adapter
						.getItem(i);
				String imgUrl = (String) hm.get("image_url");
				Log.d("Image_Path", imgUrl.toString());
				ImageLoaderTask imageLoaderTask = new ImageLoaderTask();

				HashMap<String, Object> hmDownload = new HashMap<String, Object>();
				hm.put("image_path", imgUrl);
				hm.put("position", i);

				imageLoaderTask.execute(hm);

			}

		}

	}

	private class ImageLoaderTask extends
			AsyncTask<HashMap<String, Object>, Void, HashMap<String, Object>> {

		@Override
		protected HashMap<String, Object> doInBackground(
				HashMap<String, Object>... hm) {
			InputStream iStream = null;
			String str = (String) hm[0].get("image_path");

			String[] split = str.split(".jpg");
			String img_url = null;
			img_url = split[0] + "-138.jpg";
			System.out.println(img_url);

			Log.d("New_Image_http_url", img_url);
			int position = (Integer) hm[0].get("position");

			URL url;
			try {
				url = new URL(img_url);
				Log.d("Image_http_url", img_url.toString());

				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();

				urlConnection.connect();

				iStream = urlConnection.getInputStream();
				Log.d("image Input Stream", iStream.toString());

				File cacheDirectory = getActivity().getBaseContext().getCacheDir();

				File tmpFile = new File(cacheDirectory.getAbsolutePath()
						+ "/wpta_" + position + ".png");

				FileOutputStream fOutStream = new FileOutputStream(tmpFile);

				Bitmap b = BitmapFactory.decodeStream(iStream);

				b.compress(Bitmap.CompressFormat.PNG, 100, fOutStream);

				fOutStream.flush();

				fOutStream.close();

				HashMap<String, Object> hmBitmap = new HashMap<String, Object>();

				hmBitmap.put("image_path", tmpFile.getPath());

				hmBitmap.put("position", position);

				return hmBitmap;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {

			String path = (String) result.get("image_path");
			Log.d("Cached Image Path", path.toString());

			int position = (Integer) result.get("position");

			SimpleAdapter adapter = (SimpleAdapter) mListView.getAdapter();

			@SuppressWarnings("unchecked")
			HashMap<String, Object> hm = (HashMap<String, Object>) adapter
					.getItem(position);

			hm.put("image_path", path);

			adapter.notifyDataSetChanged();
		}

	}


}
