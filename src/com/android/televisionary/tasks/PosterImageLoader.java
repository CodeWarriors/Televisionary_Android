package com.android.televisionary.tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.codewar.televisionary.mainpages.TrendingView;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class PosterImageLoader extends
AsyncTask<HashMap<String, Object>, Void, HashMap<String, Object>> {

	Context context;
	@Override
	protected HashMap<String, Object> doInBackground(
			HashMap<String, Object>... hm) {
		InputStream iStream = null;
		String str = (String) hm[0].get("image_path");

		String[] split = str.split(".jpg");
		String img_url = null;
		img_url = split[0] + "-138.jpg";
		Log.d("New_Image_http_url", img_url);
		int position = (Integer) hm[0].get("position");

		URL url;
		try {
			url = new URL(img_url);
			Log.d("Image_http_url", img_url.toString());
			// creating a Http connection to communicate with url
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();

			// connecting to url
			urlConnection.connect();

			// reading data from url
			iStream = urlConnection.getInputStream();
			Log.d("image Input Stream", iStream.toString());


			// Getting Caching directory
			File cacheDirectory;
		        	cacheDirectory = context.getCacheDir();
		//	File cacheDirectory = getActivity().getBaseContext()
		//			.getCacheDir();

			// Temporary file to store the downloaded image
			File tmpFile = new File(cacheDirectory.getPath() + "/wpta_"
					+ position + ".png");

			// The FileOutputStream to the temporary file
			FileOutputStream fOutStream = new FileOutputStream(tmpFile);

			// creating a bitmap from downloaded inputStream
			Bitmap b = BitmapFactory.decodeStream(iStream);

			// Writing the bitmap to the temporary file as png file
			b.compress(Bitmap.CompressFormat.PNG, 100, fOutStream);

			// Flush the FileOutputStream
			fOutStream.flush();

			// Close the FileOutputStream
			fOutStream.close();

			// Create a hashmap object to store image path and its position
			// in the listview
			HashMap<String, Object> hmBitmap = new HashMap<String, Object>();

			// Storing the path to the temporary image file
			hmBitmap.put("image_path", tmpFile.getPath());
			// Storing the position of the image in the listview
			hmBitmap.put("position", position);

			// Returning the HashMap object containing the image path and
			// position
			return hmBitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
