package online.daing.onlinedating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import online.dating.onlinedating.Service.ImageLoader;
import online.dating.onlinedating.adapter.MyPagerAdapter;
import online.dating.onlinedating.adapter.UserInfoExpandListAdapter;
import online.dating.onlinedating.model.User;
import online.dating.onlinedating.model.UserDetailItem;
import online.dating.onlinedating.model.UserInfoItem;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment implements OnClickListener {

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		// TODO Auto-generated method stub
		super.startActivityForResult(intent, requestCode);
	}

	public HomeFragment() {
	}

	private int lastExpandedPosition = -1;
	// private int mProgressStatus = 0;
	String UserId;

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
	int i = 0;
	TextView myProgress;
	Button okButton;

	// private Handler mHandler = new Handler();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = null;
		rootView = inflater.inflate(R.layout.profile_match, container, false);
		matchName = (TextView) rootView.findViewById(R.id.profileMatchNameAge);
		matchLocation = (TextView) rootView
				.findViewById(R.id.profileMatchLocation);

		profileImageView = (ImageView) rootView
				.findViewById(R.id.profileMatchImageView);
		LinearLayout imageLayout = (LinearLayout) rootView
				.findViewById(R.id.setDateLayout);
		Intent intent = getActivity().getIntent();
		if (intent != null) {

			SharedPreferences matchPref = getActivity().getSharedPreferences(
					"matchPref", 0);
			final String match = matchPref.getString("MatchInfo", null);

			if (match != null) {
				Log.d("Match", match);
				JSONObject matchObj;
				try {
					matchObj = new JSONObject(match);
					matchName.setText(matchObj.getString("name"));
					Geocoder geoCoder = new Geocoder(getActivity(),
							Locale.getDefault());
					JSONObject loc = matchObj.getJSONObject("location");
					List<Address> addresses = geoCoder.getFromLocation(
							Double.parseDouble(loc.getString("x")),
							Double.parseDouble(loc.getString("y")), 1);

					if (addresses.size() > 0) {
						String Location = addresses.get(0).getLocality();
						matchLocation.setText(Location);
					}
					ImageLoader imageLoader = new ImageLoader(getActivity());
					imageLoader
							.DisplayImage(
									matchObj.getJSONArray("images")
											.getString(0),
									R.drawable.com_facebook_profile_picture_blank_square,
									profileImageView);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				profileImageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getActivity(),
								MatchViewActivity.class);
						intent.putExtra(intentNameTag, matchName.getText()
								.toString());
						intent.putExtra(intentLocationTag, matchLocation
								.getText().toString());
						intent.putExtra("Match", match);
						startActivity(intent);
					}
				});
			} else {
				imageLayout.setVisibility(View.GONE);

			}

		} else {
			imageLayout.setVisibility(View.GONE);
		}

		timeProgress = (ProgressBar) rootView.findViewById(R.id.myProgress);
		// Get the Drawable custom_progressbar

		myProgress = (TextView) rootView.findViewById(R.id.myProgressText);

		Calendar cal = Calendar.getInstance();
		Long mil1 = cal.getTimeInMillis();
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 10);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		System.out.println(cal.getTime());
		Long mil2 = cal.getTimeInMillis();
		Long diff = mil2 - mil1;
		Long hours = diff / (60000 * 60);
		myProgress.setText("" + hours);
		timeProgress.setMax(24);
		timeProgress.setProgress((int) (hours + 1));

		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sendButton:

			break;

		default:
			break;
		}
	}
}
