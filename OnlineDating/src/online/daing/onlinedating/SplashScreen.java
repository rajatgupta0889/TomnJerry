package online.daing.onlinedating;

import java.util.ArrayList;

import online.dating.onlinedating.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.facebook.Session;
import com.facebook.internal.Utility;

public class SplashScreen extends Activity implements OnTaskCompleted {
	// Splash screen timer
	private static int SPLASH_TIME_OUT = 2000;
	public JSONStringer vm;
	public Context context;
	ProgressBar progBar;
	private int mProgressStatus = 0;
	private Handler mHandler = new Handler();
	String applicationId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				"pref", 0);
		context = this;
		progBar = (ProgressBar) findViewById(R.id.splashProgress);
		progBar.setVisibility(View.GONE);
		// pref.edit().putString(GetUserLogin.UserTom, null);
		// pref.edit().commit();
		// System.out.println(pref.getString(GetUserLogin.UserTom, null));
		new Thread(new Runnable() {
			public void run() {
				while (mProgressStatus < 100) {

					progBar.incrementProgressBy(1);
					
					// Update the progress bar
					mHandler.post(new Runnable() {
						public void run() {
							progBar.setProgress(mProgressStatus);
						}
					});
				}
			}

		}).start();
		if (pref.getString(GetUserLogin.UserTom, null) != null) {
			try {
				if (Session.getActiveSession() == null) {
					applicationId = Utility.getMetadataApplicationId(context);
					Session session = new Session.Builder(context)
							.setApplicationId(applicationId).build();
					Session.setActiveSession(session);
				}
				String user = pref.getString(GetUserLogin.UserTom, null);
				progBar.setVisibility(View.VISIBLE);
				Log.d("User in Splash", user);
				if (user != null) {
					String[] token = user.split(";");
					vm = new JSONStringer().object().key("name")
							.value(token[0]).key("email").value(token[1])
							.key("fbUserId").value(token[2]).key("gender")
							.value(token[3]).key("location").object().key("x")
							.value(token[4]).key("y").value(token[5])
							.endObject().endObject();
					GetUserLogin login = new GetUserLogin(context, vm);
					login.setListener(this);
					login.execute();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			new Handler().postDelayed(new Runnable() {

				/*
				 * Showing splash screen with a timer. This will be useful when
				 * you want to show case your app logo / company
				 */

				@Override
				public void run() {
					// This method will be executed once the timer is over
					// Start your app main activity
					Intent i = new Intent(SplashScreen.this, MainActivity.class);
					startActivity(i);

					// close this activity
					finish();
				}
			}, SPLASH_TIME_OUT);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTaskCompleted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnResult(String result) {
		// TODO Auto-generated method stub
		try {
			if (result != null) {
				Log.d("Result in Splash", result);

				JSONObject res = new JSONObject(result);
				progBar.setVisibility(View.GONE);
				if (res.getString("id") != null) {
					SharedPreferences userPref = context.getSharedPreferences(
							"pref", 0);

					User.tom = User.getUser(userPref.getString(
							GetUserLogin.UserTom, null));
					if (User.tom != null) {
						ArrayList<String> temp = new ArrayList<String>();
						System.out.println("Login Result" + res);
						JSONArray imageArray = res.getJSONArray("images");
						for (int i = 0; i < imageArray.length(); i++) {
							temp.add(imageArray.getString(i));
						}
						User.tom.setImageList(temp);
						if (result.contains("passions")) {
							temp = new ArrayList<String>();
							JSONArray passionArray = res
									.getJSONArray("passions");
							JSONArray passionArray1 = passionArray
									.getJSONArray(0);
							for (int i = 0; i < passionArray1.length(); i++) {
								temp.add(passionArray1.getString(i));
							}
							User.tom.setPassion(temp);
						}
						if (result.contains("orientaion")) {
							User.tom.setOrientation(res
									.getString("orientation"));
						}
						if (result.contains("height")) {
							User.tom.setHeight(res.getString("height"));
						}
						if (result.contains("profession")) {
							User.tom.setProfession(res.getString("profession"));
						}

						User.tom.setUserToken(res.getString("id"));

						SharedPreferences.Editor editor = userPref.edit();
						editor.putString(GetUserLogin.UserTom,
								User.tom.toString());
						editor.commit();
					} else {
						User.tom = User.getUser(result);
					}
					Intent i = new Intent(SplashScreen.this,
							LoginActivity.class);
					startActivity(i);
					finish();
				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated
			// catch block
			e.printStackTrace();

		}

	}

	@Override
	public void onResult(String result, String resultType) {
		// TODO Auto-generated method stub

	}
}
