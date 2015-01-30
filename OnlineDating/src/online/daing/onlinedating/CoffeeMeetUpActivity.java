package online.daing.onlinedating;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import online.dating.onlinedating.Service.ImageLoader;
import online.dating.onlinedating.model.ServiceHandler;

import org.json.JSONException;
import org.json.JSONStringer;

import android.app.Activity;
import android.content.Intent;
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

public class CoffeeMeetUpActivity extends Activity implements OnClickListener {
	private final static int Request_SetDate = 10;
	private final static int Request_SetLocation = 11;
	String[] monthName = { "jan", "feb", "mar", "apr", "may", "jun", "jul",
			"aug", "sep", "oct", "nov", "dec" };
	LinearLayout setDateLayout;
	LinearLayout setLocationLayout;
	TextView dateTV, dayTV, monthTV, locationTV;
	Button askButton;
	String date;
	String fbUserId;
	JSONStringer coffeData;
	TextView userNameTV;
	ImageView userIconIV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		getActionBar().setDisplayUseLogoEnabled(false);
		if (bundle != null && !bundle.isEmpty()) {
			setContentView(R.layout.coffee_meet);
			init();
			userNameTV.setText(bundle.getString(HomeFragment.intentNameTag));
			ImageLoader imageLoader = new ImageLoader(getApplicationContext());
			imageLoader.DisplayImage(bundle.getString("ImageIcon"),
					R.drawable.com_facebook_profile_default_icon, userIconIV);
			if (bundle.getString("requestType").equalsIgnoreCase("buddyList")) {

				fbUserId = bundle.getString("fbUserId");

				setDateLayout.setOnClickListener(this);

				setLocationLayout.setOnClickListener(this);

				askButton.setOnClickListener(this);
			} else {
				locationTV.setText(bundle.getString("location"));
				String date = bundle.getString("datetime");
				String[] dateArray = date.split(" ");
				dayTV.setText(dateArray[0]);
				monthTV.setText(dateArray[1]);
				dateTV.setText(dateArray[2]);
				askButton.setEnabled(false);
			}
		}
	}

	public void init() {
		locationTV = (TextView) findViewById(R.id.locationTextView);
		dateTV = (TextView) findViewById(R.id.date);
		dayTV = (TextView) findViewById(R.id.dateDay);
		monthTV = (TextView) findViewById(R.id.dateMonth);
		userNameTV = (TextView) findViewById(R.id.coffeeMeetUserName);
		userIconIV = (ImageView) findViewById(R.id.profilePicImageView);
		askButton = (Button) findViewById(R.id.acceptButton);
		setDateLayout = (LinearLayout) findViewById(R.id.setDateLayout);
		setLocationLayout = (LinearLayout) findViewById(R.id.setLocationLayout);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Request_SetDate) {
			if (resultCode == RESULT_OK) {
				Bundle dateData = data.getExtras();
				if (dateData != null) {
					date = dateData.getString("Date");

					String[] dateArray = date.split(" ");
					dayTV.setText(dateArray[0]);
					dateTV.setText(dateArray[2]);
					monthTV.setText(dateArray[1]);

				} else {
					Toast.makeText(getApplicationContext(), "No Date Set",
							Toast.LENGTH_LONG).show();
				}
			}
		}
		if (requestCode == Request_SetLocation) {
			System.out.println(resultCode);
			if (resultCode == RESULT_OK) {
				Bundle addressBundle = data.getExtras();
				if (addressBundle != null) {
					String address = addressBundle.getString("Address");
					locationTV.setText(address);
					Double loclat = addressBundle.getDouble("locLat");
					Double locLong = addressBundle.getDouble("locLong");

				} else {
					Toast.makeText(getApplicationContext(), "No Address Set",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	private int getMonth(String month) {
		for (int i = 0; i < monthName.length; i++) {
			if (monthName[i].equalsIgnoreCase(month)) {
				return i;
			}

		}
		return -1;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.acceptButton:
			askForCoffee();
			break;
		case R.id.setDateLayout:
			setDateTime();
			break;
		case R.id.setLocationLayout:
			setLocation();
			break;

		default:
			break;
		}

	}

	public void askForCoffee() {
		String location = locationTV.getText().toString();
		String dateTime = null;
		Calendar cal = Calendar.getInstance();
		String[] dateArray = date.split(" ");
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		cal.set(Calendar.DATE, Integer.parseInt(dateArray[2]));
		cal.set(Calendar.MONTH, getMonth(dateArray[1]));
		SimpleDateFormat format = new SimpleDateFormat(
				"EEE MMM dd yyyy hh:mm:ss zzz");

		dateTime = format.format(cal.getTime());
		dateTime = dateTime + " (IST)";
		if (!location.isEmpty() && dateTV.getText() != "") {
			try {
				coffeData = new JSONStringer().object().key("datetime")
						.value(dateTime).key("location").value(location)
						.key("invitedFbUserId").value(fbUserId).endObject();

				Log.d("Coffee Request", " " + coffeData);

				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						ServiceHandler sh = new ServiceHandler();
						String result = sh.makeServiceCall(GetUserLogin.url
								+ "coffee/invite", ServiceHandler.POST,
								coffeData);
						System.out.println("Coffee Data Result " + result);
						return null;
					}
				}.execute();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Toast.makeText(getApplicationContext(),
					"You request has been sent", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getApplicationContext(),
					LoginActivity.class);
			startActivity(intent);
			finish();
		}

		Toast.makeText(getApplicationContext(),
				"Please Select correct date and location", Toast.LENGTH_LONG)
				.show();

	}

	public void setLocation() {
		Intent intent = new Intent(getApplicationContext(),
				LocationSettingActivity.class);
		String location = locationTV.getText().toString();
		if (!location.isEmpty()) {
			intent.putExtra("Address", location);
		}
		startActivityForResult(intent, Request_SetLocation);

	}

	public void setDateTime() {
		Intent intent = new Intent(getApplicationContext(),
				CoffeeSetDateActivity.class);
		if (!dateTV.getText().toString().isEmpty()) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH,
					Integer.parseInt(dateTV.getText().toString()));

			if (getMonth(monthTV.getText().toString()) != -1) {

				cal.set(Calendar.MONTH, getMonth(monthTV.getText().toString()));

			} else {
				cal.set(Calendar.MONTH, 1);
			}

			intent.putExtra("Date", cal.getTime().toString());
		}
		startActivityForResult(intent, Request_SetDate);
	}
}
