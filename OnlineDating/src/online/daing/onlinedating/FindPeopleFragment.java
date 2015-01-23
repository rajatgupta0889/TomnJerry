package online.daing.onlinedating;

import java.util.ArrayList;

import online.dating.onlinedating.adapter.BuddyListAdapter;
import online.dating.onlinedating.model.BuddyListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FindPeopleFragment extends Fragment implements OnTaskCompleted {

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

	}

	private BuddyListAdapter adapter;
	private ListView userMessageList;
	ArrayList<BuddyListItem> buddyListItems;
	private TextView buddyTextView;
	private TextView fetchingTextView;
	private ProgressBar progBar;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		buddyListItems = new ArrayList<BuddyListItem>();

		Log.d("Buddy", " " + buddyListItems);
		GetBudddyList buddyListClass = new GetBudddyList();
		buddyListClass.setListener(this);
		buddyListClass.execute();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_buddy, container,
				false);
		init(rootView);

		adapter = new BuddyListAdapter(buddyListItems, getActivity());
		userMessageList = (ListView) rootView.findViewById(R.id.buddyListView);

		Log.d("Buddy", " " + buddyListItems);

		return rootView;
	}

	private void init(View rootView) {
		buddyTextView = (TextView) rootView.findViewById(R.id.buddyTextView);
		fetchingTextView = (TextView) rootView
				.findViewById(R.id.fetchingTextView);
		progBar = (ProgressBar) rootView
				.findViewById(R.id.buddyFetchingProgressBar);
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
				String baseImageUrl = jsonObj.getString("baseImgURL");
				System.out.println("BAse Image URL " + baseImageUrl);
				/* Handle the array list here */
				for (int i = 0; i < res.length() && i < matchId.length(); i++) {
					JSONObject buddy = res.getJSONObject(i);
					JSONObject match = matchId.getJSONObject(i);
					BuddyListItem item = new BuddyListItem(
							buddy.getString("name"),
							baseImageUrl
									+ buddy.getJSONArray("images").getString(0),
							match.getString("id"));
					item.setBuddyFbId(buddy.getString("fbUserId"));
					buddyListItems.add(item);
				}
				if (buddyListItems.size() < 1) {
					buddyTextView.setVisibility(View.VISIBLE);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		userMessageList.setAdapter(adapter);
		progBar.setVisibility(View.INVISIBLE);
		fetchingTextView.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onResult(String result, String resultType) {
		// TODO Auto-generated method stub

	}

}
