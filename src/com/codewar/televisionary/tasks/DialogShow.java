package com.codewar.televisionary.tasks;

import com.codewar.televisionary.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class DialogShow extends DialogFragment{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.progress, null);
        b.setTitle("Updating Content");
        b.setView(v);
        b.setCancelable(true);
        Dialog d = b.create();
        return d;
	}
}
