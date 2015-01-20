package online.daing.onlinedating;

import java.util.ArrayList;
import java.util.Iterator;

import online.dating.onlinedating.adapter.BuddyListAdapter;
import online.dating.onlinedating.model.BuddyListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FindPeopleFragment extends Fragment implements OnTaskCompleted {

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

	}

	private BuddyListAdapter adapter;
	private ListView userMessageList;
	ArrayList<BuddyListItem> buddyListItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_buddy, container,
				false);

		buddyListItems = new ArrayList<BuddyListItem>();
		adapter = new BuddyListAdapter(buddyListItems, getActivity());
		Log.d("Buddy", " " + buddyListItems);
		GetBudddyList buddyListClass = new GetBudddyList();
		buddyListClass.setListener(this);
		buddyListClass.execute();

		userMessageList = (ListView) rootView.findViewById(R.id.buddyListView);

		Log.d("Buddy", " " + buddyListItems);
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
		// buddyListItems.add(new BuddyListItem("Kailash ",
		// R.drawable.com_facebook_profile_default_icon));
		// adapter = new BuddyListAdapter(buddyListItems, getActivity());
		// userMessageList.setAdapter(adapter);
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
				JSONObject jsonObj = new JSONObject(result);

				JSONArray res = jsonObj.getJSONArray("buddies");
				JSONArray matchId = jsonObj.getJSONArray("matches");
				/* Handle the array list here */
				for (int i = 0; i < res.length() && i < matchId.length(); i++) {
					JSONObject buddy = res.getJSONObject(i);
					JSONObject match = matchId.getJSONObject(i);
					BuddyListItem item = new BuddyListItem(
							buddy.getString("name"), 0, match.getString("id"));
					buddyListItems.add(item);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		userMessageList.setAdapter(adapter);
	}

}
