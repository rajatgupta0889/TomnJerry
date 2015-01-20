package online.daing.onlinedating;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.google.android.gms.internal.mz;

import online.dating.onlinedating.model.ServiceHandler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MatchViewActivity extends Activity {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_match_info);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#fb4e6a")));
		profileImageView = (ImageView) findViewById(R.id.profileMatchImageView);
		profileGenderImageView = (ImageView) findViewById(R.id.matchGenderImageView);
		profileNameTextView = (TextView) findViewById(R.id.matchNameTextView);
		profileLocationTextView = (TextView) findViewById(R.id.matchLocationTextView);
		profileGenderImageView = (ImageView) findViewById(R.id.matchGenderImageView);
		profileAgeTextView = (TextView) findViewById(R.id.matchAgeTextView);

		Intent intent = getIntent();
		Bundle profileBundle = intent.getExtras();
		if (!profileBundle.isEmpty()) {
			if (profileBundle.getString(HomeFragment.intentNameTag).contains(
					",")) {
				String[] nameAge = profileBundle.getString(
						HomeFragment.intentNameTag).split(",");
				matchName = nameAge[0];
				matchAge = nameAge[1];
			} else {
				matchName = profileBundle.getString(HomeFragment.intentNameTag);
			}
			matchLocation = profileBundle
					.getString(HomeFragment.intentLocationTag);

			profileAgeTextView.setText(matchAge);
			profileLocationTextView.setText(matchLocation);
			profileNameTextView.setText(matchName);
			String match = profileBundle.getString("Match");
			try {
				JSONObject matchObj = new JSONObject(match);
				fbUserId = matchObj.getString("fbUserId");
				vm = new JSONStringer();
				vm.object().key("fbUserId").value(fbUserId).endObject();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		likeImageView = (ImageView) findViewById(R.id.userLikeImageView);
		likeImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				likeImageView
						.setImageResource(R.drawable.ic_user_like_selected);
				disLikeImageView.setVisibility(View.GONE);
				SharedPreferences matchPref = getSharedPreferences("matchPref",
						0);
				SharedPreferences.Editor editor = matchPref.edit();
				editor.putString("MatchInfo", null);
				editor.commit();
//				System.out.println(matchPref.getString("MatchInfo", null));
				new MatchAcceptRequest().execute();
			}
		});
		disLikeImageView = (ImageView) findViewById(R.id.userDisLikeImageView);
		disLikeImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						UserDislikeActivity.class);
				intent.putExtra("fbUserId", fbUserId);
				startActivity(intent);
			}
		});
	}

	public class MatchAcceptRequest extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHandler sh = new ServiceHandler();
			sh.makeServiceCall(GetUserLogin.url + "accept",
					ServiceHandler.POST, vm);

			Intent intent = new Intent(getApplicationContext(),
					LoginActivity.class);
			startActivity(intent);
			finish();
			return null;
		}

	}
}
