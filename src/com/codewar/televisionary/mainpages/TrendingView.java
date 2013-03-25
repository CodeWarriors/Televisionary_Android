package com.codewar.televisionary.mainpages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.codewar.televisionary_android.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class TrendingView extends Fragment {
	GridView gridview;
	
	 // Array of strings storing country names
    String[] countries = new String[] {
        "India",
        "Pakistan",
        "Sri Lanka",
        "China",
        "Bangladesh",
        "Nepal",
        "Afghanistan",
        "North Korea",
        "South Korea",
        "Japan",
        "India",
        "Pakistan",
        "Sri Lanka",
        "China",
        "Bangladesh",
        "Nepal",
        "Afghanistan",
        "North Korea",
        "South Korea",
        "Japan"
    };
 
    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] flags = new int[]{R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
    		R.drawable.splash_image,
        };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.trending_layout, container, false);
		
		 List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
		 
	        for(int i=0;i<10;i++){
	            HashMap<String, String> hm = new HashMap<String,String>();
	         //   hm.put("txt", countries[i]);
	            hm.put("flag", Integer.toString(flags[i]) );
	            aList.add(hm);
	        }
	 
	        // Keys used in Hashmap
	        String[] from = { "flag"};
	 
	        // Ids of views in listview_layout
	        int[] to = { R.id.flag};
	 
	        // Instantiating an adapter to store each items
	        // R.layout.listview_layout defines the layout of each item
	        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), aList, R.layout.gridview_layout, from, to);
	 
	        // Getting a reference to gridview of MainActivity
	        GridView gridView = (GridView)view.findViewById(R.id.gridView1);
	 
	        // Setting an adapter containing images to the gridview
	        gridView.setAdapter(adapter);


		return view;

	}

}
