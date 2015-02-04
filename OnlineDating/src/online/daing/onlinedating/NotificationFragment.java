package online.daing.onlinedating;

import java.util.ArrayList;

import online.dating.onlinedating.adapter.NotificationListAdapter;
import online.dating.onlinedating.model.NotificationItem;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class NotificationFragment extends Fragment {
	private ArrayList<NotificationItem> notificationItems;
	private NotificationListAdapter adapter;
	private ListView mNotificationList;

	public NotificationFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_notification,
				container, false);
		notificationItems = new ArrayList<NotificationItem>();
		// notificationItems.add(new NotificationItem(
		// R.drawable.com_facebook_logo, true, "He asked you for coffee"));
		mNotificationList = (ListView) rootView
				.findViewById(R.id.notificationList);
		//
		Intent intent = getActivity().getIntent();
		if (intent != null) {
			Bundle extra = intent.getExtras();
			if (extra != null) {
				if (extra.getString("resultType").equalsIgnoreCase("match")) {
					String message = extra.getString("Match");
					notificationItems.add(new NotificationItem(
							R.drawable.com_facebook_logo, false, message));

				} else if (extra.getString("resultType").equalsIgnoreCase(
						"coffee")) {
					String message = extra.getString("coffee");

					notificationItems.add(new NotificationItem(
							R.drawable.com_facebook_logo, true, message));
					/* Other intent handling */
				}
			}
		}
		TextView notifTextview = (TextView) rootView
				.findViewById(R.id.notifTextView);
		if (notificationItems.size() < 1) {

			notifTextview.setVisibility(View.VISIBLE);
		} else {
			notifTextview.setVisibility(View.INVISIBLE);
		}
		adapter = new NotificationListAdapter(getActivity(), notificationItems);
		mNotificationList.setAdapter(adapter);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

}
