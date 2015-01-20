package online.daing.onlinedating;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {
	private MainFragement mainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//showHashKey(getApplication());
		if (savedInstanceState == null) {
			// Add the fragment on initial activity setup

			mainFragment = new MainFragement();
			getSupportFragmentManager().beginTransaction()
					.add(android.R.id.content, mainFragment).commit();

		} else {
			// Or set the fragment from restored state info
			mainFragment = (MainFragement) getSupportFragmentManager()
					.findFragmentById(android.R.id.content);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	public static void showHashKey(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					"online.daing.onlinedating", PackageManager.GET_SIGNATURES); // Your
																					// package
																					// name
																					// here

			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.i("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
		} catch (NoSuchAlgorithmException e) {
		}
	}
}
