package com.codewar.televisionary.tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

public class SingleImageLoader {

	private File cacheDir;
	private HashMap<String, Object> imageMapSingle = new HashMap<String, Object>();

	public SingleImageLoader(Context context) {

		// Find the dir to save cached images
		String sdState = android.os.Environment.getExternalStorageState();
		if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
			File sdDir = android.os.Environment.getExternalStorageDirectory();
			cacheDir = new File(sdDir, "data/televisionary/single_images");

		} else {
			cacheDir = context.getCacheDir();
		}

		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}

	public Bitmap getBitmap(String url) {
		String filename = String.valueOf(url.hashCode());
		File f = new File(cacheDir, filename);

		// Is the bitmap in our cache?
		Bitmap bitmap = BitmapFactory.decodeFile(f.getPath());
		if (bitmap != null)
			return bitmap;

		// Nope, have to download it
		try {
			bitmap = BitmapFactory.decodeStream(new URL(url).openConnection()
					.getInputStream());
			// save bitmap to cache for later
			writeFile(bitmap, f, url);
			imageMapSingle.put("path", f.getAbsoluteFile());
			imageMapSingle.put("url", url);

			return bitmap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private void writeFile(Bitmap bmp, File f, String url) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f);
			bmp.compress(Bitmap.CompressFormat.PNG, 80, out);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (Exception ex) {
			}
		}
	}

	public Bitmap displaySingleImage(String url, ImageView imageview) {

		Log.d("Received url for display Image", url);
		if (imageMapSingle.get("url") == url) {
			Bitmap bitMaptoShow = Bitmap.createBitmap((Bitmap) imageMapSingle.get("path"));
			if (bitMaptoShow != null) {
				Log.d("Image loader", "image is in cache");
				return bitMaptoShow;
			} else {
				Bitmap bmp = getBitmap(url);
				Log.d("Image loader", "not in cache");
				return bmp;
			}

		} else {
			Log.d("Image loader", "had to download ");
			Log.d("Image loader", imageMapSingle.toString());
			Bitmap bmp = getBitmap(url);
			Log.d("Image loader", imageMapSingle.toString());
			return bmp;
		}
	}

}
