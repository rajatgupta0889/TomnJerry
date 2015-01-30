package online.daing.onlinedating;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.google.android.gms.internal.mz;
import com.viewpagerindicator.CirclePageIndicator;

import online.dating.onlinedating.adapter.ImageAdapter;
import online.dating.onlinedating.adapter.ViewPagerAdapter;
import online.dating.onlinedating.model.ServiceHandler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MatchViewActivity extends Activity implements OnClickListener {
	ImageView likeImageView;
	ImageView disLikeImageView;
	ImageView profileImageView;
	TextView profileNameTextView;
	TextView profileLocationTextView;
	ImageView profileGenderImageView;
	TextView profileAgeTextView;
	String matchName, matchAge, matchLocation;
	String match;
	String fbUserId;
	JSONStringer vm;
	ViewPager viewPager;
	ImageAdapter adapter;
	CirclePageIndicator mIndicator;
	ArrayList<String> imageUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_match_info);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#fb4e6a")));
		init();

		Intent intent = getIntent();
		Bundle profileBundle = intent.getExtras();
		if (profileBundle != null && !profileBundle.isEmpty()) {

			String match = profileBundle.getString("Match");
			try {
				JSONObject matchObj = new JSONObject(match);
				fbUserId = matchObj.getString("fbUserId");
				matchName = matchObj.getString("name");
				// matchAge = matchObj.getString("age");
				JSONArray imagesArray = matchObj.getJSONArray("images");
				for (int i = 0; i < imagesArray.length() && i < 5; i++) {
					imageUrl.add(imagesArray.getString(i));
				}
				Log.d("ImageUrl", " " + imageUrl);
				adapter = new ImageAdapter(this, imageUrl);
				// Binds the Adapter to the ViewPager
				viewPager.setAdapter(adapter);
				mIndicator.setViewPager(viewPager);

				vm = new JSONStringer();
				vm.object().key("fbUserId").value(fbUserId).endObject();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			matchLocation = profileBundle
					.getString(HomeFragment.intentLocationTag);

			profileAgeTextView.setText(matchAge);
			profileLocationTextView.setText(matchLocation);
			profileNameTextView.setText(matchName);

		}

		likeImageView.setOnClickListener(this);

		disLikeImageView.setOnClickListener(this);
	}

	public void init() {
		likeImageView = (ImageView) findViewById(R.id.userLikeImageView);
		disLikeImageView = (ImageView) findViewById(R.id.userDisLikeImageView);
		profileImageView = (ImageView) findViewById(R.id.profileMatchImageView);
		profileGenderImageView = (ImageView) findViewById(R.id.matchGenderImageView);
		profileNameTextView = (TextView) findViewById(R.id.matchNameTextView);
		profileLocationTextView = (TextView) findViewById(R.id.matchLocationTextView);
		profileGenderImageView = (ImageView) findViewById(R.id.matchGenderImageView);
		profileAgeTextView = (TextView) findViewById(R.id.matchAgeTextView);
		viewPager = (ViewPager) findViewById(R.id.imagePager);
		// Pass results to ViewPagerAdapter Class
		imageUrl = new ArrayList<String>();
		// ViewPager Indicator
		mIndicator = (CirclePageIndicator) findViewById(R.id.imageIndicator);

	}

	public class MatchAcceptRequest extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getApplicationContext(),
					LoginActivity.class);
			startActivity(intent);
			finish();
			super.onPostExecute(result);
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHandler sh = new ServiceHandler();
			sh.makeServiceCall(GetUserLogin.url + "accept",
					ServiceHandler.POST, vm);

			return null;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.userDisLikeImageView:
			dislikeAction();
			break;
		case R.id.userLikeImageView:
			likeAction();
			break;
		default:
			break;
		}
	}

	private void dislikeAction() {
		Intent intent = new Intent(getApplicationContext(),
				UserDislikeActivity.class);
		intent.putExtra("fbUserId", fbUserId);
		startActivity(intent);
	}

	private void likeAction() {
		likeImageView.setImageResource(R.drawable.ic_user_like_selected);
		disLikeImageView.setVisibility(View.GONE);
		SharedPreferences matchPref = getSharedPreferences("matchPref", 0);
		SharedPreferences.Editor editor = matchPref.edit();
		editor.putString("MatchInfo", null);
		editor.commit();
		// System.out.println(matchPref.getString("MatchInfo", null));
		new MatchAcceptRequest().execute();
	}
}
