package online.daing.onlinedating;

import java.util.ArrayList;

import online.dating.onlinedating.adapter.ChatListAdapter;
import online.dating.onlinedating.model.ChatListItem;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class PhotosFragment extends Fragment {

	public PhotosFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_photos, container,
				false);
		final ListView userMessageList = (ListView) rootView
				.findViewById(R.id.userMessageList);
		ArrayList<ChatListItem> userMessageItems = new ArrayList<ChatListItem>();

		userMessageItems.add(new ChatListItem("Rajat ",
				R.drawable.com_facebook_profile_default_icon));
		userMessageItems.add(new ChatListItem("AMar ",
				R.drawable.com_facebook_profile_default_icon));
		userMessageItems.add(new ChatListItem("Parag ",
				R.drawable.com_facebook_profile_default_icon));
		userMessageItems.add(new ChatListItem("kaush ",
				R.drawable.com_facebook_profile_default_icon));
		userMessageItems.add(new ChatListItem("Saumya ",
				R.drawable.com_facebook_profile_default_icon));
		userMessageItems.add(new ChatListItem("Kailash ",
				R.drawable.com_facebook_profile_default_icon));
		ChatListAdapter adapter = new ChatListAdapter(userMessageItems,
				getActivity());
		userMessageList.setAdapter(adapter);
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
			int itemPosition = position;

			// ListView Clicked item value
			ChatListAdapter ca = (ChatListAdapter) parent.getAdapter();
			ChatListItem woid = (ChatListItem) ca.getItem(position);
			// Show Alert
			Toast.makeText(
					getActivity(),
					"Position :" + itemPosition + "  ListItem : "
							+ woid.getChatUserName(), Toast.LENGTH_LONG).show();
		}
	}
}
