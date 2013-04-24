package com.codewar.televisionary.tasks;


import com.codewar.televisionary.R;
import com.codewar.televisionary.mainpages.DetailFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class ShowAlertDialog extends DetailFragment{

	public void showAlert(Context context, String title , String message , Boolean status){
		
		AlertDialog alertDialog = new  AlertDialog.Builder(context).create( );
		
		alertDialog.setTitle(title);
		
		alertDialog.setMessage(message);
		
		if(status != null){
			//alertDialog.setIcon((status)? R.drawable.success : R.drawable.failed);
			
			alertDialog.setButton("OK", new DialogInterface.OnClickListener( ){
				
				@Override
				public void onClick(DialogInterface dialog, int which){
				}
			});
			
			alertDialog.show( );
		}
		
	}
}
