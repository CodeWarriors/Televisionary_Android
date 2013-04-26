
package com.codewar.televisionary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.codewar.televisionary.mainpages.OverviewView;
import com.codewar.televisionary.mainpages.RecentfeedsView;
import com.codewar.televisionary.mainpages.TrendingView;
import com.codewar.televisionary.sessions.SessionManager;
import com.codewar.televisionary.tasks.NetworkTestTask;
import com.codewar.televisionary.tasks.ShowAlertDialog;
import com.navdrawer.SimpleSideDrawer;

public class TelevisionaryMainView extends FragmentActivity{

	private MyAdapter	mAdapter;
	private ViewPager	mPager;
	private SimpleSideDrawer mNav;


	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.televisionary_main_view);
		
	
		 
		 
		mAdapter = new MyAdapter(getSupportFragmentManager( ));
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
		mNav = new SimpleSideDrawer(this);
        mNav.setBehindContentView(R.layout.activity_behind);
        findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
            @Override 
            public void onClick(View v) {
                mNav.toggleDrawer();
            }
        });

		

	}

	public static class MyAdapter extends FragmentPagerAdapter{

		public MyAdapter(FragmentManager fm){
			super(fm);

		}

		@Override
		public int getCount( ){
			return 3;
		}

		@Override
		public Fragment getItem(int position){
			switch(position){
			case 0:
				return new TrendingView( );
			case 1:
				return new OverviewView( );
			case 2:
				return new RecentfeedsView( );
			default:
				return null;
			}

		}

		String	pages[]	= { "Trending", "Overview", "Recent Feeds" };

		@Override
		public CharSequence getPageTitle(int position){
			return pages[position];
		}

	}
	

}
