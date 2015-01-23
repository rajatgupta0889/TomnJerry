package online.daing.onlinedating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import online.dating.onlinedating.adapter.UserInfoExpandListAdapter;
import online.dating.onlinedating.model.UserDetailItem;
import online.dating.onlinedating.model.UserInfoItem;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileUpdateActivity extends Activity {
	private int lastExpandedPosition = -1;
	ExpandableListView userList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_home);
		userList = (ExpandableListView) findViewById(R.id.expandableUserInfoListView);

		ArrayList<UserInfoItem> userInfoItems = new ArrayList<UserInfoItem>();
		userInfoItems.add(new UserInfoItem("How tall are you",
				R.drawable.ic_user_height));
		userInfoItems.add(new UserInfoItem("Your Passion",
				R.drawable.ic_user_passion));
		userInfoItems.add(new UserInfoItem("Interested in",
				R.drawable.ic_user_interest));
		userInfoItems.add(new UserInfoItem("Profession",
				R.drawable.ic_user_passion));
		Boolean[] passion = { false, false };
		Boolean[] profession = { false, false };
		UserDetailItem item = new UserDetailItem(true, 1, passion, profession);
		/* Setting imageVIew and create on click Listener */
		ImageView profileImageView = (ImageView) findViewById(R.id.profilePicImageView);
		profileImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						ProfilePicActivity.class);
				startActivity(intent);
			}
		});
		UserInfoExpandListAdapter adapter = new UserInfoExpandListAdapter(this,
				userInfoItems, item);
		userList.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				if (lastExpandedPosition != -1
						&& groupPosition != lastExpandedPosition) {
					userList.collapseGroup(lastExpandedPosition);

				}
				lastExpandedPosition = groupPosition;
			}
		});
		userList.setAdapter(adapter);
		SharedPreferences pref = getSharedPreferences("pref", 0);
		if (pref.getString(GetUserLogin.UserTom, null) != null) {

			String user = pref.getString(GetUserLogin.UserTom, null);
			if (user != null) {
				String[] token = user.split(";");
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
				TextView nameTv = (TextView) findViewById(R.id.userName);
				nameTv.setText(nameTv.getText() + token[0]);
				TextView ageTv = (TextView) findViewById(R.id.userAge);
				ageTv.setText(token[4]);
				TextView locTv = (TextView) findViewById(R.id.userLoc);
				locTv.setText(Location);
				TextView userDesigTv = (TextView) findViewById(R.id.userDesignation);
				userDesigTv.setText("");

			}
		}
	}
}
