
package com.codewar.televisionary.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.R.string;
import android.os.Environment;

public class LoadFromCache{

	StringBuilder	text	= new StringBuilder( );
	String txtName = null;

	public LoadFromCache(String txtName ){
		setOfflineJson( );
		this.txtName= txtName;
	}

	private void setOfflineJson( ){
		File sdDir = Environment.getExternalStorageDirectory( );
		File path = new File(sdDir, "data/televesionary/jsondata");
		File file = new File(path, txtName + ".txt");

		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = br.readLine( )) != null){
				text.append(line);
				text.append('\n');
			}
		} catch(IOException e){
			e.printStackTrace( );
		}
	}

	public String getOfflineJson(){
		return text.toString( );
	}
}
