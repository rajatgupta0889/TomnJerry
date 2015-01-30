package online.daing.onlinedating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import online.daing.onlinedating.animation.ResizeAnimation;
import online.dating.onlinedating.Service.ImageLoader;
import online.dating.onlinedating.adapter.UserInfoExpandListAdapter;
import online.dating.onlinedating.model.ServiceHandler;
import online.dating.onlinedating.model.User;
import online.dating.onlinedating.model.UserDetailItem;
import online.dating.onlinedating.model.UserInfoItem;

import org.json.JSONException;
import org.json.JSONStringer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileUpdateActivity extends Activity {
	private int lastExpandedPosition = -1;
	ExpandableListView userList;
	Animation slideOut, slideIn;
	RelativeLayout listLayout;
	RelativeLayout topLayout;
	RelativeLayout pullLayout;
	ResizeAnimation animation = null;
	Button doneButton;
	int height, userHeight;
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
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent))); 
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
		ImageView profileImageViewEdit = (ImageView) findViewById(R.id.editImageView);
		ImageView profileImageView = (ImageView) findViewById(R.id.profilePicImageView);
		int loader = R.drawable.com_facebook_profile_picture_blank_square;

		// ImageLoader class instance
		ImageLoader imgLoader = new ImageLoader(this);
		topLayout = (RelativeLayout) findViewById(R.id.topRelativeLayout);
		pullLayout = (RelativeLayout) findViewById(R.id.pullLayout);
		listLayout = (RelativeLayout) findViewById(R.id.listLayout);
		slideOut = AnimationUtils.loadAnimation(this, R.anim.slideout);
		SharedPreferences pref = getSharedPreferences("pref", 0);
		if (pref.getString(GetUserLogin.UserTom, null) != null) {

			String user = pref.getString(GetUserLogin.UserTom, null);
			if (user != null) {
				String[] token = user.split(";");
				User.tom = User.getUser(user);
				String Location = "";
				Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
				try {
					List<Address> addresses = geoCoder.getFromLocation(
							Double.parseDouble(token[6]),
							Double.parseDouble(token[7]), 1);

					if (addresses.size() > 0) {
						Location = addresses.get(0).getLocality();

					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
				SharedPreferences profilePref = getSharedPreferences(
						"profilePref", 0);

				String image_url = profilePref.getString("profilePic", "");
				imgLoader.DisplayImage(image_url, loader, profileImageView);
				TextView nameTv = (TextView) findViewById(R.id.userName);
				nameTv.setText(nameTv.getText() + token[0]);
				TextView ageTv = (TextView) findViewById(R.id.userAge);
				ageTv.setText(token[4]);

				TextView locTv = (TextView) findViewById(R.id.userLoc);
				locTv.setText(Location);
				TextView userDesigTv = (TextView) findViewById(R.id.userDesignation);
				userDesigTv.setText(User.tom.getProfession());

				Boolean[] passion = new Boolean[6];
				if (User.tom != null) {
					if (User.tom.getPassion().size() < 1) {
						for (int i = 0; i < passion.length; i++) {
							passion[i] = false;
						}
					} else {
						if (User.tom.getPassion().contains("Traveling")) {
							passion[0] = false;
						} else {
							passion[0] = true;
						}
						if (User.tom.getPassion().contains("Partying")) {
							passion[1] = false;
						} else {
							passion[1] = true;
						}
						if (User.tom.getPassion().contains("Sports")) {
							passion[2] = false;
						} else {
							passion[2] = true;
						}
						if (User.tom.getPassion().contains("Hyking")) {
							passion[3] = false;
						} else {
							passion[3] = true;
						}
						if (User.tom.getPassion().contains("Cycling")) {
							passion[4] = false;
						} else {
							passion[4] = true;
						}
						if (User.tom.getPassion().contains("Painting")) {
							passion[5] = false;
						} else {
							passion[5] = true;
						}
					}
					Boolean[] profession = new Boolean[6];
					if (User.tom.getProfession().isEmpty()) {
						for (int i = 0; i < passion.length; i++) {
							profession[i] = false;
						}
					} else {
						if (User.tom.getProfession().equalsIgnoreCase(
								"Software Engg.")) {
							profession[0] = false;
						} else {
							profession[0] = true;
						}
						if (User.tom.getPassion().contains("Graphics Designer")) {
							profession[1] = false;
						} else {
							profession[1] = true;
						}
						if (User.tom.getPassion().contains("Management")) {
							profession[2] = false;
						} else {
							profession[2] = true;
						}
						if (User.tom.getPassion().contains("CA")) {
							profession[3] = false;
						} else {
							profession[3] = true;
						}
						if (User.tom.getPassion().contains("Data Analyst")) {
							profession[4] = false;
						} else {
							profession[4] = true;
						}
						if (User.tom.getPassion().contains("Researcher")) {
							profession[5] = false;
						} else {
							profession[5] = true;
						}
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
				}
				UserInfoExpandListAdapter adapter = new UserInfoExpandListAdapter(
						this, userInfoItems, item);
				userList.setAdapter(adapter);
			}
		}

		profileImageViewEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						ProfilePicActivity.class);
				startActivity(intent);
			}
		});
		height = topLayout.getHeight();
		Log.d("Aniation", "Top Layout Height" + height);

		userList.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {

				// topLayout.startAnimation(slideOut);

				animation = new ResizeAnimation(topLayout, topLayout
						.getHeight(), 0, true);

				animation.setAdjacentHeightIncrement(topLayout.getHeight());
				animation.setDuration(500);
				animation.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						height = topLayout.getHeight();
						pullLayout.setVisibility(View.VISIBLE);

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						Log.d("Animation",
								"end layout Height  " + topLayout.getHeight());
						topLayout.setVisibility(View.GONE);

					}
				});
				animation.setAdjacentView(listLayout);
				topLayout.startAnimation(animation);

				if (lastExpandedPosition != -1
						&& groupPosition != lastExpandedPosition) {
					userList.collapseGroup(lastExpandedPosition);

				}
				lastExpandedPosition = groupPosition;
			}
		});

		pullLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// topLayout.startAnimation(slideIn);
				animation = new ResizeAnimation(topLayout, height, true);
				animation.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						Log.d("Animation", "start + layout Height  " + height);
						topLayout.setVisibility(View.VISIBLE);
						pullLayout.setVisibility(View.GONE);

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						Log.d("Animation",
								"end  layout Height  " + topLayout.getHeight());

					}
				});
				userList.collapseGroup(lastExpandedPosition);
				animation.setDuration(500);
				animation.setAdjacentView(listLayout);
				animation.setAdjacentHeightIncrement(height);

				topLayout.startAnimation(animation);

			}
		});
		doneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JSONStringer updateData = new JSONStringer();
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
					updateData.object().key("orientation").value(orien)
							.key("height").value(tempHeight).array()
							.key("passions").value(item.getPassionArrayList())
							.endArray().key("profession")
							.value(item.getUserProfession()).endObject();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				User.tom.setOrientation(orien);
				User.tom.setHeight(tempHeight);
				User.tom.setPassion(item.getPassionArrayList());
				User.tom.setProfession(item.getUserProfession());
				SharedPreferences pref = getSharedPreferences("pref", 0);
				Editor editor = pref.edit();
				editor.putString(GetUserLogin.UserTom, User.tom.toString());
				editor.commit();
				UpdateProfile updateProfile = new UpdateProfile(updateData,
						getApplicationContext());
				updateProfile.execute();

				Toast.makeText(getApplicationContext(),
						item.getUserProfession(), Toast.LENGTH_LONG).show();
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
			finish();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHandler sh = new ServiceHandler();
			sh.makeServiceCall(GetUserLogin.url + "profile",
					ServiceHandler.POST, data);
			return null;
		}

	}
}
