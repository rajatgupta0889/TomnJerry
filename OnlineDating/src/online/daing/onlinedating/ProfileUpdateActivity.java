package online.daing.onlinedating;

import java.util.ArrayList;

import online.daing.onlinedating.animation.ResizeAnimation;
import online.dating.onlinedating.adapter.UserInfoExpandListAdapter;
import online.dating.onlinedating.model.ServiceHandler;
import online.dating.onlinedating.model.User;
import online.dating.onlinedating.model.UserDetailItem;
import online.dating.onlinedating.model.UserInfoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONStringer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class ProfileUpdateActivity extends Activity {
	private int lastExpandedPosition = -1;
	ExpandableListView userList;

	ResizeAnimation animation = null;
	Button doneButton;
	int userHeight;
	Boolean orientaion;
	UserDetailItem item;
	JSONStringer updateData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_home);
		userList = (ExpandableListView) findViewById(R.id.expandableUserInfoListView);
		getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent)));
		ArrayList<UserInfoItem> userInfoItems = new ArrayList<UserInfoItem>();
		userInfoItems.add(new UserInfoItem("How tall are you",
				R.drawable.ic_user_height));
		userInfoItems.add(new UserInfoItem("Your Passion",
				R.drawable.ic_user_passion));
		userInfoItems.add(new UserInfoItem("Interested in",
				R.drawable.ic_user_interest));
		userInfoItems.add(new UserInfoItem("Profession",
				R.drawable.ic_user_passion));

		doneButton = (Button) findViewById(R.id.okButton);

		/* Setting imageVIew and create on click Listener */

		SharedPreferences pref = getSharedPreferences("pref", 0);
		if (pref.getString(GetUserLogin.UserTom, null) != null) {

			String user = pref.getString(GetUserLogin.UserTom, null);
			if (user != null) {
				User.tom = User.getUser(user);
				if (User.tom != null) {
					System.out.println(User.tom.getPassion());
					Boolean[] passion = { false, false, false, false, false,
							false };

					if (User.tom.getPassion().size() > 1) {

						if (User.tom.getPassion().contains("Traveling")) {
							passion[0] = true;
						}
						if (User.tom.getPassion().contains("Partying")) {
							passion[1] = true;
						}
						if (User.tom.getPassion().contains("Sports")) {
							passion[2] = true;
						}
						if (User.tom.getPassion().contains("Hyking")) {
							passion[3] = true;
						}
						if (User.tom.getPassion().contains("Cycling")) {
							passion[4] = true;
						}
						if (User.tom.getPassion().contains("Painting")) {
							passion[5] = true;
						}
					}
					Boolean[] profession = { false, false, false, false, false,
							false };

					if (User.tom.getProfession().equalsIgnoreCase(
							"Software Engg.")) {
						profession[0] = true;
					}
					if (User.tom.getProfession().equalsIgnoreCase(
							"Graphics Designer")) {
						profession[1] = true;
					}
					if (User.tom.getProfession().equalsIgnoreCase("Management")) {
						profession[2] = true;
					}
					if (User.tom.getProfession().equalsIgnoreCase("CA")) {
						profession[3] = true;
					}
					if (User.tom.getProfession().equalsIgnoreCase(
							"Data Analyst")) {
						profession[4] = true;
					}
					if (User.tom.getProfession().equalsIgnoreCase("Researcher")) {
						profession[5] = true;
					}

					if (User.tom.getHeight().equalsIgnoreCase("low")) {
						userHeight = 0;
					} else if (User.tom.getHeight().equalsIgnoreCase("medium")) {
						userHeight = 1;
					} else {
						userHeight = 2;
					}

					if (User.tom.getOrientation().equalsIgnoreCase("straight")) {
						if (User.tom.getGender().equalsIgnoreCase("male")) {
							orientaion = false;
						} else {
							orientaion = true;
						}

					} else if (User.tom.getOrientation()
							.equalsIgnoreCase("gay")) {
						if (User.tom.getGender().equalsIgnoreCase("male")) {
							orientaion = true;
						}
					} else {
						orientaion = false;
					}

					item = new UserDetailItem(orientaion, userHeight, passion,
							profession);
					if (item.getUserProfession() == null
							|| item.getUserProfession().isEmpty()) {
						item.setUserProfession(User.tom.getProfession());
					}
				}
				UserInfoExpandListAdapter adapter = new UserInfoExpandListAdapter(
						this, userInfoItems, item);
				userList.setAdapter(adapter);
			}
		}

		userList.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {

				// topLayout.startAnimation(slideOut);
				if (lastExpandedPosition != -1
						&& groupPosition != lastExpandedPosition) {
					userList.collapseGroup(lastExpandedPosition);

				}
				lastExpandedPosition = groupPosition;
			}
		});

		doneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (item.getPassionArrayList().isEmpty()) {
					item.setPassionArrayList(User.tom.getPassion());
				}
				String orien;
				if (User.tom.getGender().equals("male")) {
					if (item.getIsMale()) {
						orien = "gay";
					} else {
						orien = "straight";
					}
				} else {
					if (item.getIsMale()) {
						orien = "straight";
					} else {
						orien = "lesbian";
					}
				}
				String tempHeight;
				if (item.getHeight() == 0) {
					tempHeight = "low";
				} else if (item.getHeight() == 1) {
					tempHeight = "medium";
				} else {
					tempHeight = "long";
				}
				try {
					JSONArray array = new JSONArray(item.getPassionArrayList());
					updateData = new JSONStringer().object().key("orientation")
							.value(orien).key("height").value(tempHeight)
							.key("passions").array().value(array).endArray()
							.key("profession").value(item.getUserProfession())
							.endObject();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				User.tom.setOrientation(orien);
				User.tom.setHeight(tempHeight);
				System.out.println("Item Passion " + item.getPassionArrayList());
				User.tom.setPassion(item.getPassionArrayList());
				User.tom.setProfession(item.getUserProfession());
				SharedPreferences pref = getSharedPreferences("pref", 0);
				Editor editor = pref.edit();
				editor.putString(GetUserLogin.UserTom, User.tom.toString());
				editor.commit();
				System.out.println("UpdateData " + updateData);
				UpdateProfile updateProfile = new UpdateProfile(updateData,
						getApplicationContext());
				updateProfile.execute();

			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			Intent homeIntent = new Intent(this, LoginActivity.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
			finish();
		}
		return (super.onOptionsItemSelected(menuItem));
	}

	public void onBackPressed() {
		// do whatever you want the 'Back' button to do
		// as an example the 'Back' button is set to start a new Activity named
		// 'NewActivity'
		this.startActivity(new Intent(this, LoginActivity.class));
		finish();
		return;
	}

	public class UpdateProfile extends AsyncTask<Void, Void, Void> {

		JSONStringer data;
		Context context;

		public UpdateProfile(JSONStringer data, Context context) {
			super();
			this.data = data;
			this.context = context;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(context, "User Updated", Toast.LENGTH_SHORT).show();
			Intent homeIntent = new Intent(context, LoginActivity.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
			finish();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if (data != null) {
				ServiceHandler sh = new ServiceHandler();
				sh.makeServiceCall(GetUserLogin.url + "profile",
						ServiceHandler.POST, data);
			}
			return null;
		}

	}
}
