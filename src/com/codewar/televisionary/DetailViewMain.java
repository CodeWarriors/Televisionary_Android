package com.codewar.televisionary;

import com.codewar.televisionary.TelevisionaryMainView.MyAdapter;
import com.codewar.televisionary.mainpages.DetailFragment;
import com.codewar.televisionary.mainpages.OverviewView;
import com.codewar.televisionary.mainpages.RecentfeedsView;
import com.codewar.televisionary.mainpages.SeasonsFragment;
import com.codewar.televisionary.mainpages.TrendingView;
import com.navdrawer.SimpleSideDrawer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

public class DetailViewMain extends FragmentActivity {
	
	private MyAdapter mAdapter;
	private ViewPager mPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_main_view);
		
		mAdapter=   new MyAdapter(getSupportFragmentManager());
		mPager = (ViewPager)findViewById(R.id.pager_detailview);
		mPager.setAdapter(mAdapter);
			
	}

	public static class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new DetailFragment();
			case 1:
				return new SeasonsFragment();
			default:
				return null;
			}

		}
		
		String pages[] = {"Summary","Seasons"};
		@Override
	    public CharSequence getPageTitle(int position) {
	        return pages[position];
	    }



	}
	
}
