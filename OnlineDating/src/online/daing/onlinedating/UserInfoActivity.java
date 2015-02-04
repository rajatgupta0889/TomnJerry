package online.daing.onlinedating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import online.daing.onlinedating.R.id;
import online.dating.onlinedating.Service.ImageLoader;
import online.dating.onlinedating.model.User;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserInfoActivity extends Activity implements OnClickListener {
	TextView name, profession, location, age, passion;
	ImageView userImageView;
	String Location;
	String passionString, professionString;
	LinearLayout profLayout, passionLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		init();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#fb4e6a")));
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent)));
		SharedPreferences pref = getSharedPreferences("pref", 0);
		String user = pref.getString(GetUserLogin.UserTom, null);
		if (user != null) {
			User.tom = User.getUser(user);
			if (User.tom != null) {
				name.setText(User.tom.getName());
				if (User.tom.getGender().equals("male")) {
					age.setText("M " + User.tom.getAge());
				} else {
					age.setText("F " + User.tom.getAge());
				}
				Double locx = User.tom.getLocationX();
				Double locy = User.tom.getLocationY();
				Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
				try {
					List<Address> addresses = geoCoder.getFromLocation(locy,
							locx, 1);

					if (addresses.size() > 0) {
						Location = addresses.get(0).getLocality();

					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (Location != null && !Location.isEmpty()) {
					location.setText(Location);
				}
				profession.setText(User.tom.getProfession());

			}
			SharedPreferences profilePref = getSharedPreferences("profilePref",
					0);
			System.out.println(profilePref.getString("profilePic", ""));
			ImageLoader imageLoader = new ImageLoader(this);
			imageLoader
					.DisplayImage(profilePref.getString("profilePic", ""),
							R.drawable.com_facebook_profile_default_icon,
							userImageView);

			userImageView.setOnClickListener(this);
			ArrayList<String> temp = User.tom.getPassion();
			System.out.println("Passion List : " + temp);
			if (temp != null && !temp.isEmpty()) {
				for (String passionTemp : temp) {
					if (passionString == null || passionString.isEmpty()) {
						if (passionTemp.contains("[")) {
							passionString = passionTemp.substring(passionTemp
									.lastIndexOf("[") + 1);
						} else {
							passionString = passionTemp;
						}
					} else {
						if (passionTemp.contains("]")) {
							passionString = passionString
									+ ", "
									+ passionTemp.substring(0,
											passionTemp.indexOf("]"));
						} else {
							passionString = passionString + ", " + passionTemp;
						}
					}
				}
			}
			if (passionString != null && !passionString.isEmpty())
				passion.setText(passionString);
			else {
				passion.setText("Tap here to update you passion");
				passionLayout.setOnClickListener(this);
			}
			professionString = User.tom.getProfession();
			if (professionString != null && !professionString.isEmpty())
				profession.setText(professionString);
			else {
				profession.setText("Tap here to update you profession");
				profLayout.setOnClickListener(this);
			}

		}
	}

	private void init() {
		name = (TextView) findViewById(R.id.userNameTextView);
		age = (TextView) findViewById(id.userAgeTextView);
		profession = (TextView) findViewById(R.id.userProfessionTextView);
		location = (TextView) findViewById(R.id.userLocationTextView);
		userImageView = (ImageView) findViewById(R.id.userImageView);
		passion = (TextView) findViewById(R.id.userPassionTextView);
		profLayout = (LinearLayout) findViewById(R.id.profLayout);
		passionLayout = (LinearLayout) findViewById(R.id.matchPassionLayout);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.userImageView:
			goToProfileActivity();
			break;
		case R.id.profLayout:
			gotoUpdateProfile();
			break;
		case R.id.matchPassionLayout:
			gotoUpdateProfile();
			break;
		default:
			break;
		}

	}

	private void gotoUpdateProfile() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(),
				ProfileUpdateActivity.class);
		startActivity(intent);
		finish();

	}

	private void goToProfileActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, ProfilePicActivity.class);
		startActivity(intent);

	}
}
