package online.daing.onlinedating;

import java.util.ArrayList;

import online.dating.onlinedating.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class SplashScreen extends Activity implements OnTaskCompleted {
	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;
	public JSONStringer vm;
	public Context context;
	ProgressDialog proDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				"pref", 0);
		context = this;
		proDialog = new ProgressDialog(context);
		// pref.edit().putString(GetUserLogin.UserTom, null);
		// pref.edit().commit();
		// System.out.println(pref.getString(GetUserLogin.UserTom, null));
		if (pref.getString(GetUserLogin.UserTom, null) != null) {
			try {
				String user = pref.getString(GetUserLogin.UserTom, null);

				Log.d("User in Splash", user);
				if (user != null) {
					String[] token = user.split(";");
					vm = new JSONStringer().object().key("name")
							.value(token[0]).key("email").value(token[1])
							.key("fbUserId").value(token[2]).key("gender")
							.value(token[3]).key("location").object().key("x")
							.value(token[4]).key("y").value(token[5])
							.endObject().endObject();
					GetUserLogin login = new GetUserLogin(context, proDialog,
							vm);
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
							temp.add(imageArray.getString(0));
						}
						User.tom.setImageList(temp);
						User.tom.setUserToken(res.getString("id"));

						SharedPreferences.Editor editor = userPref.edit();
						editor.putString(GetUserLogin.UserTom,
								User.tom.toString());
						editor.commit();
					} else {
						User.tom = User.getUser(result);

					}
					proDialog.dismiss();
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
