package online.daing.onlinedating;

import java.util.ArrayList;

import online.dating.onlinedating.model.ServiceHandler;

import org.json.JSONException;
import org.json.JSONStringer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class UserDislikeActivity extends Activity implements
		OnCheckedChangeListener {
	Button disLikeRequestButton;
	CheckBox interestCB, positionCB, imageCB, heightCB;
	String reasonForDislike = "";
	JSONStringer dislIkeData;
	ArrayList<String> reasonForPass;
	String fbUserId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_dislike);
		interestCB = (CheckBox) findViewById(R.id.interestCB);
		positionCB = (CheckBox) findViewById(R.id.positionCB);
		imageCB = (CheckBox) findViewById(R.id.imageCB);
		heightCB = (CheckBox) findViewById(R.id.heightCB);
		reasonForPass = new ArrayList<String>();
		interestCB.setOnCheckedChangeListener(this);
		positionCB.setOnCheckedChangeListener(this);
		imageCB.setOnCheckedChangeListener(this);
		heightCB.setOnCheckedChangeListener(this);
		Intent intent = getIntent();
		Bundle extra = intent.getExtras();
		if (extra != null) {
			fbUserId = extra.getString("fbUserId");

		}
		disLikeRequestButton = (Button) findViewById(R.id.goButton);

		disLikeRequestButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					dislIkeData = new JSONStringer();
					reasonForPass.add("not_educated_enough");

					dislIkeData.object().key("reasonForPass").array()
							.value(reasonForPass).endArray().key("fbUserId")
							.value(fbUserId).endObject();
					System.out.println(dislIkeData);
					SharedPreferences matchPref = getSharedPreferences(
							"matchPref", 0);
					SharedPreferences.Editor editor = matchPref.edit();
					editor.putString("MatchInfo", null);
					editor.commit();
					if (!reasonForPass.isEmpty()) {
						new UserRejectMatch().execute(dislIkeData);
						Toast.makeText(getApplicationContext(),
								"Dislike The user", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(),
								"Please Select a reeason", Toast.LENGTH_SHORT)
								.show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	public class UserRejectMatch extends AsyncTask<JSONStringer, Void, Void> {

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		protected Void doInBackground(JSONStringer... params) {
			// TODO Auto-generated method stub
			ServiceHandler sh = new ServiceHandler();
			sh.makeServiceCall(GetUserLogin.url + "reject",
					ServiceHandler.POST, params[0]);
			Intent intent = new Intent(getApplicationContext(),
					LoginActivity.class);
			startActivity(intent);
			finish();
			return null;
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {
			reasonForPass.add(((CheckBox) buttonView).getText().toString());
		} else {
			reasonForPass.remove(((CheckBox) buttonView).getText().toString());
		}
	}

}
