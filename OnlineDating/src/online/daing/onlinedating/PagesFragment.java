package online.daing.onlinedating;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Session;

public class PagesFragment extends Fragment implements OnClickListener {

	public PagesFragment() {
	}

	Button editButtton, shareButton, logOutButton, userInfoButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.settings, container, false);
		init(rootView);
		editButtton.setOnClickListener(this);
		shareButton.setOnClickListener(this);
		logOutButton.setOnClickListener(this);
		userInfoButton.setOnClickListener(this);
		return rootView;
	}

	public void init(View view) {
		editButtton = (Button) view.findViewById(R.id.editButton);
		shareButton = (Button) view.findViewById(R.id.shareButton);
		logOutButton = (Button) view.findViewById(R.id.logOutButton);
		userInfoButton = (Button) view.findViewById(R.id.infoEditButton);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.editButton:
			userUpdateUserAction();
			break;
		case R.id.shareButton:
			appShareAction();
			break;
		case R.id.logOutButton:
			userLogOutAction();
			break;
		case R.id.infoEditButton:
			userInfo();
			break;

		default:
			break;
		}
	}

	private void userInfo() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), UserInfoActivity.class);
		startActivity(intent);
	}

	private void userLogOutAction() {
		// TODO Auto-generated method stub
		Session session = Session.getActiveSession();
		session.closeAndClearTokenInformation();
		SharedPreferences pref = getActivity().getSharedPreferences("pref", 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(GetUserLogin.UserTom, null);
		Toast.makeText(getActivity(), "you are logged out", Toast.LENGTH_SHORT)
				.show();
		editor.commit();
		Intent intent = new Intent(getActivity(), MainActivity.class);
		startActivity(intent);
		getActivity().finish();
	}

	private void appShareAction() {
		// TODO Auto-generated method stub
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
	}

	private void userUpdateUserAction() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), ProfileUpdateActivity.class);
		startActivity(intent);

	}

}
