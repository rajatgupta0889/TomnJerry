package online.daing.onlinedating;

import online.dating.onlinedating.model.ServiceHandler;
import online.dating.onlinedating.model.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CoffeeMeetUpRequestActivity extends Activity implements
		OnClickListener {
	LinearLayout setDateLayout;
	LinearLayout setLocationLayout;
	TextView dateTV, dayTV, monthTV, locationTV;
	Button acceptButton, rejectButton;
	String date;
	String coffeeId;
	JSONStringer coffeData;
	TextView userNameTV;
	ImageView userIconIV;
	String FbUserId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		setContentView(R.layout.coffee_meet_request);
		if (bundle != null && !bundle.isEmpty()) {
			init();

			if (User.tom == null) {
				SharedPreferences pref = getSharedPreferences("pref", 0);
				String user = pref.getString(GetUserLogin.UserTom, null);
				if (user != null) {
					User.tom = User.getUser(user);
				}
			}
			FbUserId = User.tom.getFbUserId();
			
			String coffeeData = bundle.getString("coffeeData");
			if (coffeeData != null) {
				// Log.d("Coffee Data ", coffeeData);

				try {
					JSONObject coffeeObj = new JSONObject(coffeeData);
					String date = coffeeObj.getString("datetime");
					String[] dateArray = date.split(" ");
					dayTV.setText(dateArray[0]);
					monthTV.setText(dateArray[1]);
					dateTV.setText(dateArray[2]);
					String location = coffeeObj.getString("location");
					locationTV.setText(location);
					coffeeId = coffeeObj.getString("id");
					if (coffeeObj.getString("inviteeFbUserId")
							.equalsIgnoreCase(FbUserId)) {

						JSONObject invitedUser = coffeeObj
								.getJSONObject("invitedUser");
						userNameTV.setText(invitedUser.getString("name"));

					} else {

						JSONObject inviteeobj = coffeeObj
								.getJSONObject("inviteeUser");
						userNameTV.setText(inviteeobj.getString("name"));
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println(bundle);
				String date = bundle.getString("datetime");
				String[] dateArray = date.split(" ");
				dayTV.setText(dateArray[0]);
				monthTV.setText(dateArray[1]);
				dateTV.setText(dateArray[2]);
				String location = bundle.getString("location");
				locationTV.setText(location);
				userNameTV
						.setText(bundle.getString(HomeFragment.intentNameTag));
				coffeeId = bundle.getString("id");
			}
			acceptButton = (Button) findViewById(R.id.acceptButton);
			acceptButton.setOnClickListener(this);
			rejectButton = (Button) findViewById(R.id.rejectButton);
			rejectButton.setOnClickListener(this);
		}
	}

	public void init() {
		userNameTV = (TextView) findViewById(R.id.coffeeMeetUserName);
		userIconIV = (ImageView) findViewById(R.id.profilePicImageView);
		locationTV = (TextView) findViewById(R.id.locationTextView);
		dateTV = (TextView) findViewById(R.id.date);
		dayTV = (TextView) findViewById(R.id.dateDay);
		monthTV = (TextView) findViewById(R.id.dateMonth);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.acceptButton:
			request("accept");
			break;
		case R.id.rejectButton:
			request("reject");
			break;
		default:
			break;
		}
	}

	private void request(final String reqType) {
		new AsyncTask<Void, Void, Void>() {
			String result;

			protected void onPostExecute(Void result) {
				if (reqType.equalsIgnoreCase("accept")) {
					Toast.makeText(getApplicationContext(),
							"You accept the request", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"You reject the request", Toast.LENGTH_LONG).show();

				}
				Intent intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(intent);
				finish();
			};

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				ServiceHandler sh = new ServiceHandler();
				if (reqType.equalsIgnoreCase("accept")) {
					System.out.println("Accept  " + GetUserLogin.url
							+ "coffee/accept/" + coffeeId);
					result = sh.makeServiceCall(GetUserLogin.url
							+ "coffee/accept/" + coffeeId, ServiceHandler.GET);

				} else {
					System.out.println("Reject  " + GetUserLogin.url
							+ "coffee/accept/" + coffeeId);
					result = sh.makeServiceCall(GetUserLogin.url
							+ "coffee/deny/" + coffeeId, ServiceHandler.GET);
				}

				Log.d("Coffee Req Result", result);
				return null;
			}

		}.execute();

	}
}
