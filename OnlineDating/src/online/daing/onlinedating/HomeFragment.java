package online.daing.onlinedating;

import java.util.ArrayList;

import online.dating.onlinedating.adapter.MyPagerAdapter;
import online.dating.onlinedating.adapter.UserInfoExpandListAdapter;
import online.dating.onlinedating.model.UserDetailItem;
import online.dating.onlinedating.model.UserInfoItem;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	public HomeFragment() {
	}

	private int lastExpandedPosition = -1;

	public final static int PAGES = 5;
	// You can choose a bigger number for LOOPS, but you know, nobody will fling
	// more than 1000 times just in order to test your "infinite" ViewPager :D
	public final static int LOOPS = 1000;
	public final static int FIRST_PAGE = PAGES * LOOPS / 2;
	public final static float BIG_SCALE = 1.0f;
	public final static float SMALL_SCALE = 0.7f;
	public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;
	final String PREFS_NAME = "pref";
	private ProgressBar timeProgress;
	public MyPagerAdapter adapter;
	public ViewPager pager;
	public ImageView profileImageView;
	TextView matchName;
	TextView matchLocation;
	public final static String intentNameTag = "Name";
	public final static String intentLocationTag = "Location";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = null;
		SharedPreferences settings = getActivity().getSharedPreferences(
				PREFS_NAME, 0);
		if (settings.getBoolean("my_first_time", true)) {
			// the app is being launched for first time)
			rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
			final ExpandableListView userList = (ExpandableListView) rootView
					.findViewById(R.id.expandableUserInfoListView);

			ArrayList<UserInfoItem> userInfoItems = new ArrayList<UserInfoItem>();
			userInfoItems.add(new UserInfoItem("How tall are you",
					R.drawable.ic_user_height));
			userInfoItems.add(new UserInfoItem("Your Passion",
					R.drawable.ic_user_passion));
			userInfoItems.add(new UserInfoItem("Interested in",
					R.drawable.ic_user_interest));
			userInfoItems.add(new UserInfoItem("Profession",
					R.drawable.ic_user_passion));
			Boolean[] passion = { false, false };
			Boolean[] profession = { false, false };
			UserDetailItem item = new UserDetailItem(true, 1, passion,
					profession);

			UserInfoExpandListAdapter adapter = new UserInfoExpandListAdapter(
					getActivity(), userInfoItems, item);
			userList.setOnGroupExpandListener(new OnGroupExpandListener() {

				@Override
				public void onGroupExpand(int groupPosition) {
					if (lastExpandedPosition != -1
							&& groupPosition != lastExpandedPosition) {
						userList.collapseGroup(lastExpandedPosition);
					}
					lastExpandedPosition = groupPosition;
				}
			});
			userList.setAdapter(adapter);
		} else {
			rootView = inflater.inflate(R.layout.profile_match, container,
					false);
			// pager = (ViewPager) rootView.findViewById(R.id.myviewpager);
			//
			// adapter = new MyPagerAdapter(this, getChildFragmentManager());
			// pager.setAdapter(adapter);
			// pager.setOnPageChangeListener(adapter);
			//
			// // Set current item to the middle page so we can fling to both
			// // directions left and right
			// pager.setCurrentItem(FIRST_PAGE);
			//
			// // Necessary or the pager will only have one extra page to show
			// // make this at least however many pages you can see
			// pager.setOffscreenPageLimit(3);
			//
			// // Set margin for pages as a negative number, so a part of next
			// and
			// // previous pages will be showed
			// pager.setPageMargin(-350);
			matchName = (TextView) rootView
					.findViewById(R.id.profileMatchNameAge);
			matchLocation = (TextView) rootView
					.findViewById(R.id.profileMatchLocation);

			profileImageView = (ImageView) rootView
					.findViewById(R.id.profileMatchImageView);

			profileImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(),
							MatchViewActivity.class);
					intent.putExtra(intentNameTag, matchName.getText()
							.toString());
					intent.putExtra(intentLocationTag, matchLocation.getText()
							.toString());
					startActivity(intent);
				}
			});

		}

		if (settings.getBoolean("my_first_time", true)) {
			// the app is being launched for first time, do something
			Log.d("Comments", "First time");

			// first time task

			// record the fact that the app has been started at least once
			settings.edit().putBoolean("my_first_time", false).commit();
		} else {
			settings.edit().putBoolean("my_first_time", true).commit();

		}
		timeProgress = (ProgressBar) rootView.findViewById(R.id.myProgress);

		return rootView;
	}
}
