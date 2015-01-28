package online.daing.onlinedating;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import online.dating.onlinedating.adapter.ChatListAdapter;
import online.dating.onlinedating.model.BuddyListItem;
import online.dating.onlinedating.model.ChatListItem;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class PhotosFragment extends Fragment implements OnTaskCompleted {

	public PhotosFragment() {
	}

	ArrayList<ChatListItem> userMessageItems;
	ChatListAdapter adapter;
	ListView userMessageList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_user_messages,
				container, false);
		userMessageList = (ListView) rootView
				.findViewById(R.id.userMessageList);
		userMessageItems = new ArrayList<ChatListItem>();
		GetBudddyList buddyListClass = new GetBudddyList();
		buddyListClass.setListener(this);
		buddyListClass.execute();

		adapter = new ChatListAdapter(userMessageItems, getActivity());

		userMessageList.setOnItemClickListener(new UserMessageListItemClick());

		return rootView;
	}

	private class UserMessageListItemClick implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			// selectItem(position);
			// ListView Clicked item index

			// ListView Clicked item value
			ChatListAdapter ca = (ChatListAdapter) parent.getAdapter();
			ChatListItem item = (ChatListItem) ca.getItem(position);

			Intent intent = new Intent(getActivity(), UserMessageActivity.class);
			intent.putExtra(HomeFragment.intentNameTag, item.getChatUserName());
			intent.putExtra("ImageIcon", item.getChatUserIcon());
			intent.putExtra("id", item.getBuddyId());
			startActivity(intent);
			// // Show Alert
			// Toast.makeText(
			// getActivity(),
			// "Position :" + itemPosition + "  ListItem : "
			// + woid.getChatUserName(), Toast.LENGTH_LONG).show();
		}
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
				/* Handle the array list here */
				for (int i = 0; i < res.length() && i < matchId.length(); i++) {
					JSONObject buddy = res.getJSONObject(i);
					JSONObject match = matchId.getJSONObject(i);
					

					ChatListItem item = new ChatListItem(
							buddy.getString("name"),
							baseImageUrl
									+ buddy.getJSONArray("images").getString(0),
							match.getString("id"));

					userMessageItems.add(item);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		userMessageList.setAdapter(adapter);
	}

	@Override
	public void onResult(String result, String resultType) {
		// TODO Auto-generated method stub

	}
}
