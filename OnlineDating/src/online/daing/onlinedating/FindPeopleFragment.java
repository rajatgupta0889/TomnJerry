package online.daing.onlinedating;

import java.util.ArrayList;

import online.dating.onlinedating.adapter.BuddyListAdapter;
import online.dating.onlinedating.model.BuddyListItem;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FindPeopleFragment extends Fragment implements OnTaskCompleted {

	private BuddyListAdapter adapter;
	private ListView userMessageList;
	ArrayList<BuddyListItem> buddyListItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_buddy, container,
				false);
		GetBudddyList buddyListClass = new GetBudddyList();
		buddyListClass.setListener(this);
		buddyListClass.execute();

		userMessageList = (ListView) rootView.findViewById(R.id.buddyListView);

		buddyListItems = new ArrayList<BuddyListItem>();

		// buddyListItems.add(new BuddyListItem("Rajat ",
		// R.drawable.com_facebook_profile_default_icon));
		// buddyListItems.add(new BuddyListItem("AMar ",
		// R.drawable.com_facebook_profile_default_icon));
		// buddyListItems.add(new BuddyListItem("Parag ",
		// R.drawable.com_facebook_profile_default_icon));
		// buddyListItems.add(new BuddyListItem("kaush ",
		// R.drawable.com_facebook_profile_default_icon));
		// buddyListItems.add(new BuddyListItem("Saumya ",
		// R.drawable.com_facebook_profile_default_icon));
		buddyListItems.add(new BuddyListItem("Kailash ",
				R.drawable.com_facebook_profile_default_icon));
		adapter = new BuddyListAdapter(buddyListItems, getActivity());
		userMessageList.setAdapter(adapter);
		return rootView;
	}

	@Override
	public void onTaskCompleted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnResult(String result) {
		// TODO Auto-generated method stub
		if (result != null) {
			try {

				JSONObject res = new JSONObject(result);
				/* Handle the array list here */

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		adapter = new BuddyListAdapter(buddyListItems, getActivity());
		userMessageList.setAdapter(adapter);
	}

}
