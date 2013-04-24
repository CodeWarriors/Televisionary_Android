package com.codewar.televisionary.jsonarrayget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;

public class HttpJsonArrayGet {
	private static byte[] buff = new byte[1024];
	boolean isSdAvail = true;
	boolean isSdWriteable =true;

	public static synchronized String downloadFromServer(String... params)
			throws Exception {
		String retval = null;

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(params[0]);

		try {
			HttpResponse responce = client.execute(request);
			Log.d("Http responce", responce.toString( ));
		

			StatusLine status = responce.getStatusLine();
			if (status.getStatusCode() != 200) {
				// handle error here
				throw new Exception("Invalid responce from trakt.tv"
						+ status.toString());
			}

			// process the content
			HttpEntity entity = responce.getEntity();
			InputStream ist = entity.getContent();
			ByteArrayOutputStream content = new ByteArrayOutputStream();

			int readCount = 0;
			while ((readCount = ist.read(buff)) != -1) {
				content.write(buff, 0, readCount);
			}
			retval = new String(content.toByteArray());
			
			
			
		} catch (Exception e) {
			throw new Exception(
					"Problem connecting to server" + e.getMessage(), e);
		}
		
		//writing json data to file
		HttpJsonArrayGet s = new  HttpJsonArrayGet();
		s.checkSD( );
		s.WriteFile(retval);
		return retval;
	}
	
	private void checkSD(){
		String state = Environment.getExternalStorageState( );
		if(Environment.MEDIA_MOUNTED.equals(state)){
			//write
			isSdAvail = true;
			isSdWriteable =true;
		}else if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
			//read only
			isSdAvail = true;
			isSdWriteable =false;
		}else{
			isSdAvail = false;
			isSdWriteable =false;
		}
	}
	
	private void WriteFile(String data){
		if(isSdAvail && isSdWriteable){
			File sdDir = Environment.getExternalStorageDirectory();
			File path = new File(sdDir,"data/televesionary/jsondata");
			String name = "trendinglist";
			File file = new File(path, name+".txt");
			
			path.mkdir( );
			
			try{
				OutputStream os = new FileOutputStream(file);
	            os.write(data.getBytes( ));
	            os.close( );
            } catch(IOException e){
	            e.printStackTrace();
            }
			
			
		}
	}
}
