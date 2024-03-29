
package com.codewar.televisionary.tasks;

import com.codewar.televisionary.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogManager{

	public void showAlertDialog(Context context, String title, String message,
	        Boolean status){

		AlertDialog alertDialog = new AlertDialog.Builder(context).create( );

		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
		alertDialog.setIcon(R.drawable.alerts_and_states_error);

		alertDialog.show( );
	}
}
