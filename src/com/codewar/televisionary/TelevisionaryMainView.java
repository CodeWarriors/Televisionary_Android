package com.codewar.televisionary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.codewar.televisionary.mainpages.OverviewView;
import com.codewar.televisionary.mainpages.RecentfeedsView;
import com.codewar.televisionary.mainpages.TrendingView;

public class TelevisionaryMainView extends FragmentActivity {
 
	private MyAdapter mAdapter;
	private ViewPager mPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.televisionary_main_view);
		
		mAdapter=   new MyAdapter(getSupportFragmentManager());
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
		

	}

	public static class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);

		}
		
		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new TrendingView();
			case 1:
				return new OverviewView();
			case 2:
				return new RecentfeedsView();
			default:
				return null;
			}

		}
		
		String pages[] = {"Trending","Overview","Recent Feeds"};
		@Override
	    public CharSequence getPageTitle(int position) {
	        return pages[position];
	    }



	}

}
