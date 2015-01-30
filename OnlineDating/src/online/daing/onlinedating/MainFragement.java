package online.daing.onlinedating;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import online.dating.onlinedating.adapter.ViewPagerAdapter;
import online.dating.onlinedating.model.GPSTracker;
import online.dating.onlinedating.model.ServiceHandler;
import online.dating.onlinedating.model.User;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
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
import com.google.android.gms.internal.lp;
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
	String userID;

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
	String[] text, textAnswer;
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
		text = new String[] { "Looking for a Mate? ",
				"Get New Friend Suggestions Everyday",
				"Got a Favourite Hangout Place? Pin it!",
				"Say it All! Show it All!" };
		textAnswer = new String[] {
				"Find out the special one in your life",
				"Upvote profiles you like and upon affirmation, get going! As simple as that!",
				"DateApp’s unique features lets you choose your favourite coffee house or discotheque. Pin it on the map, mark it on the calendar!",
				"DateApp’s cool messaging interface lets you stay in touch and share your moments! Share exciting memories and pictures with just a tap!" };
		// Locate the ViewPager in viewpager_main.xml
		viewPager = (ViewPager) view.findViewById(R.id.pager);
		// Pass results to ViewPagerAdapter Class
		adapter = new ViewPagerAdapter(context, text, textAnswer);
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
		authButton.setReadPermissions(Arrays.asList("public_profile", "email",
				"user_location", "user_birthday", "user_likes"));

		return view;
	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");

			System.out.println("Session " + session);
			if (logIn == 0) {
				logIn = 1;
				new Request(session, "/me", null, HttpMethod.GET,
						new Request.Callback() {
							public void onCompleted(Response response) {
								/* handle the result */
								System.out.println("Response " + response);
								if (response != null) {
									GraphObject resp = response
											.getGraphObject();

									user = resp.getInnerJSONObject();
									Log.d(TAG, " " + user);
									if (user != null) {

										try {
											userID = user.getString("id");
											String age = "";
											if (user.has("birthday")) {
												String birthday = user
														.getString("birthday");

												if (birthday != null) {
													Calendar dateOfBirth = Calendar
															.getInstance();
													String[] dob = birthday
															.split("/");
													dateOfBirth.set(
															Integer.parseInt(dob[2]),
															Integer.parseInt(dob[0]),
															Integer.parseInt(dob[1]));
													Calendar today = Calendar
															.getInstance();
													int year = today
															.get(Calendar.YEAR)
															- dateOfBirth
																	.get(Calendar.YEAR);

													if (today
															.get(Calendar.DAY_OF_YEAR) < dateOfBirth
															.get(Calendar.DAY_OF_YEAR)) {
														year--;
													}
													Integer ageInt = new Integer(
															year);
													age = ageInt.toString();

												}
											}
											User.tom = new User(user
													.getString("name"), user
													.getString("email"), user
													.getString("id"), user
													.getString("gender"),
													latitude, longitude, "",
													getRegistrationId(context),
													"android", age,
													new ArrayList<String>());

											vm = new JSONStringer()
													.object()
													.key("name")
													.value(User.tom.getName())
													.key("email")
													.value(User.tom.getEmail())
													.key("fbUserId")
													.value(User.tom
															.getFbUserId())
													.key("gender")
													.value(User.tom.getGender())
													.key("location")
													.object()
													.key("x")
													.value(User.tom
															.getLocationX())
													.key("y")
													.value(User.tom
															.getLocationY())
													.endObject()
													.key("device_token")
													.value(User.tom
															.getDeviceToken())
													.key("os")
													.value(User.tom.getOs())
													.endObject();

											Log.d("MainFragement",
													"Sending String "
															+ vm.toString());
											GetUserLogin login = new GetUserLogin(
													getActivity(), proDialog,
													vm);
											login.setListener(new OnTaskCompleted() {

												@Override
												public void onTaskCompleted() {
													// TODO Auto-generated
													// method
													// stub
													new AsyncTask<Void, Void, Void>() {

														@Override
														protected Void doInBackground(
																Void... params) {
															// TODO
															// Auto-generated
															// method stub
															Bitmap userIcon = getFacebookProfilePicture(userID);
															ByteArrayOutputStream baos = new ByteArrayOutputStream();
															userIcon.compress(
																	Bitmap.CompressFormat.JPEG,
																	100, baos);
															byte[] b = baos
																	.toByteArray();
															String imageEncoded = Base64
																	.encodeToString(
																			b,
																			Base64.DEFAULT);

															JSONStringer data = null;
															try {
																data = new JSONStringer()
																		.object()
																		.key("data")
																		.value(imageEncoded)
																		.endObject();
																ServiceHandler sh = new ServiceHandler();
																String result = sh
																		.makeServiceCall(
																				GetUserLogin.url
																						+ "image",
																				ServiceHandler.POST,
																				data);
																System.out
																		.println("Result "
																				+ result);
															} catch (JSONException e) {
																// TODO
																// Auto-generated
																// catch block
																e.printStackTrace();
															}
															return null;
														}

													}.execute();

												}

												@Override
												public void OnResult(
														String result) {

													SharedPreferences pref = context
															.getSharedPreferences(
																	"pref", 0);
													SharedPreferences.Editor editor = pref
															.edit();
													if (User.tom != null) {
														editor.putString(
																GetUserLogin.UserTom,
																User.tom.toString());
														editor.commit();
														Log.d("Result in MainFrag oNResult",
																pref.getString(
																		GetUserLogin.UserTom,
																		null));
													} else {
														Log.d(TAG,
																"User is null");
													}

													Intent intent = new Intent(
															getActivity(),
															ProfileUpdateActivity.class);
													startActivity(intent);
													getActivity().finish();
												}

												@Override
												public void onResult(
														String result,
														String resultType) {
													// TODO Auto-generated
													// method stub

												}
											});
											login.execute();
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								} else {
									new AlertDialog.Builder(context)
											.setTitle("Error in Connection")
											.setMessage("Login after sometime")
											.setPositiveButton(
													android.R.string.yes,
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface dialog,
																int which) {
															// continue with
															// delete
															((Activity) context)
																	.finish();
														}
													})
											.setIcon(
													android.R.drawable.ic_dialog_alert)
											.show();

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
			logIn = 0;
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
		logIn = 0;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	public Bitmap getFacebookProfilePicture(String userID) {
		URL imageURL;
		Bitmap bitmap = null;
		try {
			imageURL = new URL("https://graph.facebook.com/" + userID
					+ "/picture?type=large");
			SharedPreferences pref = context.getSharedPreferences(
					"profilePref", 0);
			SharedPreferences.Editor editor = pref.edit();
			editor.putString("profilePic", imageURL.toString());
			editor.commit();
			bitmap = BitmapFactory.decodeStream(imageURL.openConnection()
					.getInputStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;

	}
}
