package online.daing.onlinedating;

import java.util.ArrayList;

import online.dating.onlinedating.adapter.NotificationListAdapter;
import online.dating.onlinedating.model.NotificationItem;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PagesFragment extends Fragment {
	private ArrayList<NotificationItem> notificationItems;
	private NotificationListAdapter adapter;
	private ListView mNotificationList;

	public PagesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_notification, container,
				false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mNotificationList = (ListView) getActivity().findViewById(
				R.id.notificationList);
		notificationItems = new ArrayList<NotificationItem>();
		notificationItems.add(new NotificationItem(
				R.drawable.com_facebook_profile_picture_blank_square, false,
				"Sarah Adds you as a freind"));
		notificationItems.add(new NotificationItem(
				R.drawable.com_facebook_profile_picture_blank_square, true,
				"Sarah sends you Coffee request"));
		/*
		 * notificationItems.add(new NotificationItem(
		 * R.drawable.com_facebook_profile_picture_blank_square, false,
		 * "Melisa Adds you as a freind"));
		 * 
		 * notificationItems.add(new NotificationItem(
		 * R.drawable.com_facebook_profile_picture_blank_square, true,
		 * "Melisa sends you Coffee request")); notificationItems.add(new
		 * NotificationItem(
		 * R.drawable.com_facebook_profile_picture_blank_square, false,
		 * "Andy Adds you as a freind")); notificationItems.add(new
		 * NotificationItem(
		 * R.drawable.com_facebook_profile_picture_blank_square, false,
		 * "Andy Adds you as a freind"));
		 */
		adapter = new NotificationListAdapter(getActivity(), notificationItems);
		mNotificationList.setAdapter(adapter);

	}

}
