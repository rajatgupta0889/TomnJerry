package online.daing.onlinedating;

import org.json.JSONException;
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
		SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", 0);
		context = this;
		proDialog = new ProgressDialog(context);
		Log.i("Splash","Log "+pref.getString("Id","") + "id");
		if (pref.getString("Id", "") != ""
				&& pref.getString("fbUserId", "") != "") {
			try {
				vm = new JSONStringer().object().key("name")
						.value(pref.getString("name", "")).key("email")
						.value(pref.getString("email", "")).key("fbUserId")
						.value(pref.getString("fbUserId", "")).key("id")
						.value(pref.getString("Id", "")).endObject();
				
				GetUserLogin login = new GetUserLogin(context, proDialog, vm);
				login.setListener(this);
				login.execute();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			new Handler().postDelayed(new Runnable() {
			 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
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
		proDialog.dismiss();
		Intent i = new Intent(SplashScreen.this, LoginActivity.class);
		startActivity(i);
		finish();
	}
}
