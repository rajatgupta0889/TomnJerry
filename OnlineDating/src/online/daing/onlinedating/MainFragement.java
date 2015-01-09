package online.daing.onlinedating;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import online.dating.onlinedating.adapter.ViewPagerAdapter;
import online.dating.onlinedating.model.GPSTracker;
import online.dating.onlinedating.model.User;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.viewpagerindicator.CirclePageIndicator;

@SuppressLint("NewApi")
public class MainFragement extends Fragment {

	private static final String TAG = "MainFragment";
	private static String url = "http://54.88.90.102:1337/";
	public static int logIn = 0;
	private UiLifecycleHelper uiHelper;
	ProgressDialog proDialog;
	private static JSONObject user;
	List<NameValuePair> nameValuePairs;
	static JSONStringer vm = new JSONStringer();
	GPSTracker gps;
	double latitude;
	double longitude;
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	static User tom;
	/**
	 * Substitute you own sender ID here. This is the project number you got
	 * from the API Console, as described in "Getting Started."
	 */
	String SENDER_ID = "907505440449";

	/**
	 * Tag used on log messages.
	 */
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	SharedPreferences prefs;
	Context context;
	String regid;
	ViewPager viewPager;
	PagerAdapter adapter;
	String[] rank;
	String[] country;
	String[] population;
	int[] flag;
	CirclePageIndicator mIndicator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();

		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(getActivity());
			regid = getRegistrationId(context);

			if (regid.isEmpty()) {
				registerInBackground();
			}

			gps = new GPSTracker(getActivity());
			/*
			 * if(Session.getActiveSession() != null){ startActivity(new
			 * Intent(getActivity(),LoginActivity.class)); }
			 */
			uiHelper = new UiLifecycleHelper(getActivity(), callback);
			if (gps.canGetLocation()) {
				latitude = gps.getLatitude();
				longitude = gps.getLongitude();

				uiHelper.onCreate(savedInstanceState);
			} else {
				gps.showSettingsAlert();
			}
		} else {
			Log.i(TAG, "No valid Google Play Services APK found.");
		}
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If it
	 * doesn't, display a dialog that allows users to download the APK from the
	 * Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity());
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode,
						getActivity(), PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				getActivity().finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return getActivity().getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and app versionCode in the application's
	 * shared preferences.
	 */
	@SuppressWarnings("unchecked")
	private void registerInBackground() {
		new AsyncTask() {
			@Override
			protected Object doInBackground(Object... params) {
				// TODO Auto-generated method stub
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regid;
					Log.d(TAG, msg);
					// You should send the registration ID to your server over
					// HTTP,
					// so it can use GCM/HTTP or CCS to send messages to your
					// app.
					// The request to your server should be authenticated if
					// your app
					// is using accounts.
					sendRegistrationIdToBackend();

					// For this demo: we don't need to send it because the
					// device
					// will send upstream messages to a server that echo back
					// the
					// message using the 'from' address in the message.

					// Persist the regID - no need to register again.
					storeRegistrationId(context, regid);
				} catch (IOException ex) {
					// msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}
				return null;
			}

		}.execute(null, null, null);
	}

	/**
	 * Sends the registration ID to your server over HTTP, so it can use
	 * GCM/HTTP or CCS to send messages to your app. Not needed for this demo
	 * since the device sends upstream messages to a server that echoes back the
	 * message using the 'from' address in the message.
	 */
	private void sendRegistrationIdToBackend() {
		// Your implementation here.
	}

	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main, container, false);
		LoginButton authButton = (LoginButton) view
				.findViewById(R.id.authButton);
		rank = new String[] { "1", "2", "3", "4" };

		country = new String[] { "China", "India", "United States", "Indonesia" };

		population = new String[] { "1,354,040,000", "1,210,193,422",
				"315,761,000", "237,641,326" };

		flag = new int[] { R.drawable.coffe_pink, R.drawable.coffe_pink,
				R.drawable.coffe_pink, R.drawable.coffe_pink };

		// Locate the ViewPager in viewpager_main.xml
		viewPager = (ViewPager) view.findViewById(R.id.pager);
		// Pass results to ViewPagerAdapter Class
		adapter = new ViewPagerAdapter(context, rank, country, population, flag);
		// Binds the Adapter to the ViewPager
		viewPager.setAdapter(adapter);

		// ViewPager Indicator
		mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
		mIndicator.setViewPager(viewPager);
		((CirclePageIndicator) mIndicator).setSnap(true);
		authButton.setFragment(this);
		Log.i(TAG, authButton.getText().toString());
		// if (Session.getActiveSession().getAccessToken() != null) {
		// authButton.setVisibility(View.INVISIBLE);
		// }
		authButton.setReadPermissions(Arrays.asList("public_profile", "email"));

		return view;
	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {

			Log.i(TAG, "Logged in...");

			System.out.println("Session " + session);
			if (logIn == 0) {
				new Request(session, "/me", null, HttpMethod.GET,
						new Request.Callback() {
							public void onCompleted(Response response) {
								/* handle the result */

								GraphObject resp = response.getGraphObject();

								user = resp.getInnerJSONObject();
								System.out.println(user);

								if (user != null) {

									try {
										tom = new User(user.getString("name"),
												user.getString("email"), user
														.getString("id"), user
														.getString("gender"),
												longitude, latitude, "");

										vm = new JSONStringer().object()
												.key("name")
												.value(tom.getName())
												.key("email")
												.value(tom.getEmail())
												.key("fbUserId")
												.value(tom.getFbUserId())
												.key("gender")
												.value(tom.getGender())
												.key("location").object()
												.key("x")
												.value(tom.getLocationX())
												.key("y")
												.value(tom.getLocationY())
												.endObject().endObject();
										/*
										 * nameValuePairs = new
										 * ArrayList<NameValuePair>();
										 * nameValuePairs.add(new
										 * BasicNameValuePair( "name",
										 * user.getString("name")));
										 * nameValuePairs.add(new
										 * BasicNameValuePair( "email", user
										 * .getString("email"))); nameValuePairs
										 * .add(new BasicNameValuePair(
										 * "fbUserId", user.getString("id")));
										 * nameValuePairs.add(new
										 * BasicNameValuePair( "gender", user
										 * .getString("gender")));
										 * List<NameValuePair> local = new
										 * ArrayList<NameValuePair>();
										 * local.add(new BasicNameValuePair("x",
										 * "12.966")); local.add(new
										 * BasicNameValuePair("y", "77.566"));
										 * System.out.println(local.toString());
										 * nameValuePairs.add(new
										 * BasicNameValuePair("location",
										 * local.toString()));
										 */
										Log.d("MainFragement",
												"Sending String "
														+ vm.toString());
										GetUserLogin login = new GetUserLogin(
												getActivity(), proDialog, vm);
										login.setListener(new OnTaskCompleted() {

											@Override
											public void onTaskCompleted() {
												// TODO Auto-generated method
												// stub

											}

											@Override
											public void OnResult(String result) {
												// TODO Auto-generated method
												// stub

											}
										});
										login.execute();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}

						}).executeAsync();
			}
			/*
			 * else{
			 * 
			 * }
			 */
		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
			logIn = 0;
		}

	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}
		if (gps.canGetLocation()) {
			uiHelper.onResume();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

}
