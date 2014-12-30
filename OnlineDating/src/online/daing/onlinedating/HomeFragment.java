package online.daing.onlinedating;

import java.util.ArrayList;
import java.util.HashMap;

import online.dating.onlinedating.adapter.UserInfoExpandListAdapter;
import online.dating.onlinedating.model.UserDetailItem;
import online.dating.onlinedating.model.UserInfoItem;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class HomeFragment extends Fragment {

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		ExpandableListView userList = (ExpandableListView) rootView
				.findViewById(R.id.expandableUserInfoListView);

		ArrayList<UserInfoItem> userInfoItems = new ArrayList<UserInfoItem>();
		userInfoItems.add(new UserInfoItem("How tall are you",
				R.drawable.ic_user_height));
		userInfoItems.add(new UserInfoItem("Your Passion",
				R.drawable.ic_user_passion));
		userInfoItems.add(new UserInfoItem("Interested in",
				R.drawable.ic_user_interest));
		userInfoItems.add(new UserInfoItem("Profession",
				R.drawable.ic_user_passion));
		HashMap<UserInfoItem, UserDetailItem> userDetailItems = new HashMap<UserInfoItem, UserDetailItem>();
		Boolean[] passion = { false, false };
		Boolean[] profession = { false, false };
		UserDetailItem item = new UserDetailItem(true, 1, passion, profession);
		
		UserInfoExpandListAdapter adapter = new UserInfoExpandListAdapter(
				getActivity(), userInfoItems, item);
		userList.setAdapter(adapter);
		
		return rootView;
	}
}
