package online.daing.onlinedating;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

public class CoffeeSetDateActivity extends FragmentActivity {
	private CaldroidFragment caldroidFragment;
	private String intentDate = null;

	@SuppressWarnings("deprecation")
	private void setCustomResourceForDates() {
		Calendar cal = Calendar.getInstance();

		// Min date is last 7 days
		cal.add(Calendar.DATE, 0);
		Date blueDate;
		if (intentDate != null) {
			System.out.println("Intent String" + intentDate);
			blueDate = new Date(intentDate);
			System.out.println("Blue date " + blueDate);
		} else {
			blueDate = cal.getTime();
		}
		if (caldroidFragment != null) {
			caldroidFragment.setBackgroundResourceForDate(R.color.blue,
					blueDate);

			caldroidFragment.setTextColorForDate(R.color.white, blueDate);
			caldroidFragment.clearDisableDates();

		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coffee_meetup_setdate);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		Bundle intentData = intent.getExtras();
		Date dateFromIntent = null;
		Calendar intentCal = null;
		if (intentData != null) {
			intentDate = intentData.getString("Date");
			dateFromIntent = new Date(intentDate);
			intentCal = Calendar.getInstance();
			intentCal.setTime(dateFromIntent);
		}
		final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

		// Setup caldroid fragment
		// **** If you want normal CaldroidFragment, use below line ****
		caldroidFragment = new CaldroidFragment();

		// caldroidFragment = new CaldroidSampleCustomFragment();

		// Setup arguments

		// If Activity is created after rotation
		if (savedInstanceState != null) {
			caldroidFragment.restoreStatesFromKey(savedInstanceState,
					"CALDROID_SAVED_STATE");
		}
		// If activity is created from fresh
		else {
			Bundle args = new Bundle();
			Calendar cal = Calendar.getInstance();
			if (intentCal != null) {
				args.putInt(CaldroidFragment.MONTH,
						intentCal.get(Calendar.MONTH) + 1);
				args.putInt(CaldroidFragment.YEAR, intentCal.get(Calendar.YEAR));

			} else {
				args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
				args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
			}
			args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
			args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, false);
			// Uncomment this to customize startDayOfWeek
			// args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
			// CaldroidFragment.TUESDAY); // Tuesday
			caldroidFragment.setArguments(args);
		}

		setCustomResourceForDates();

		// Attach to the activity
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		t.commit();

		// Setup listener
		final CaldroidListener listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {

				caldroidFragment.setBackgroundResourceForDate(R.color.green,
						date);
				System.out.println(date);
				if (date.after(Calendar.getInstance().getTime())) {
					Intent intent = new Intent();
					intent.putExtra("Date", date.toString());
					setResult(RESULT_OK, intent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Please Select Correct Date", Toast.LENGTH_SHORT)
							.show();
				}
			}

			@Override
			public void onChangeMonth(int month, int year) {
				String text = "month: " + month + " year: " + year;
				Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onLongClickDate(Date date, View view) {
				Toast.makeText(getApplicationContext(),
						"Long click " + formatter.format(date),
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCaldroidViewCreated() {
				if (caldroidFragment.getLeftArrowButton() != null) {
					// Toast.makeText(getApplicationContext(),
					// "Caldroid view is created", Toast.LENGTH_SHORT)
					// .show();
				}
			}

		};
		caldroidFragment.setCaldroidListener(listener);
	}

	/**
	 * Save current states of the Caldroid here
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		if (caldroidFragment != null) {
			caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
		}

	}

}
