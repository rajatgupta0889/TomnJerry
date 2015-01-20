package online.daing.onlinedating;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CoffeeMeetUpActivity extends Activity {
	private final static int Request_SetDate = 10;
	private final static int Request_SetLocation = 11;
	String[] monthName = { "jan", "feb", "mar", "apr", "may", "jun", "jul",
			"aug", "sep", "oct", "nov", "dec" };
	LinearLayout setDateLayout;
	LinearLayout setLocationLayout;
	TextView dateTV, dayTV, monthTV, locationTV;
	Button askButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coffee_meet);
		TextView userNameTV = (TextView) findViewById(R.id.coffeeMeetUserName);
		ImageView userIconIV = (ImageView) findViewById(R.id.coffeeMeetUserIcon);
		locationTV = (TextView) findViewById(R.id.locationTextView);
		dateTV = (TextView) findViewById(R.id.date);
		dayTV = (TextView) findViewById(R.id.dateDay);
		monthTV = (TextView) findViewById(R.id.dateMonth);
		setDateLayout = (LinearLayout) findViewById(R.id.matchDisplayLayout);
		setDateLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						CoffeeSetDateActivity.class);
				if (!dateTV.getText().toString().isEmpty()) {
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.DAY_OF_MONTH,
							Integer.parseInt(dateTV.getText().toString()));

					if (getMonth(monthTV.getText().toString()) != -1) {

						cal.set(Calendar.MONTH, getMonth(monthTV.getText()
								.toString()));

					} else {
						cal.set(Calendar.MONTH, 1);
					}

					intent.putExtra("Date", cal.getTime().toString());
				}
				startActivityForResult(intent, Request_SetDate);
			}
		});
		setLocationLayout = (LinearLayout) findViewById(R.id.setLocationLayout);
		setLocationLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						LocationSettingActivity.class);
				String location = locationTV.getText().toString();
				if (!location.isEmpty()) {
					intent.putExtra("Address", location);
				}
				startActivityForResult(intent, Request_SetLocation);
			}
		});
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null && !bundle.isEmpty()) {
			userNameTV.setText(bundle.getString(HomeFragment.intentNameTag));
			userIconIV.setImageResource(bundle.getInt("ImageIcon"));
		}
		askButton = (Button) findViewById(R.id.setSettingButton);
		askButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "A request for coffee",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Request_SetDate) {
			if (resultCode == RESULT_OK) {
				Bundle dateData = data.getExtras();
				if (dateData != null) {
					String date = dateData.getString("Date");
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
}
