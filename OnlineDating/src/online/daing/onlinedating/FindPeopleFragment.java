package online.daing.onlinedating;

import java.util.ArrayList;

import online.dating.onlinedating.adapter.BuddyListAdapter;
import online.dating.onlinedating.model.BuddyListItem;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FindPeopleFragment extends Fragment {
	
	public FindPeopleFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_buddy, container, false);
         
        final ListView userMessageList = (ListView) rootView
				.findViewById(R.id.buddyListView);
		ArrayList<BuddyListItem> buddyListItems = new ArrayList<BuddyListItem>();

		buddyListItems.add(new BuddyListItem("Rajat ",
				R.drawable.com_facebook_profile_default_icon));
		buddyListItems.add(new BuddyListItem("AMar ",
				R.drawable.com_facebook_profile_default_icon));
		buddyListItems.add(new BuddyListItem("Parag ",
				R.drawable.com_facebook_profile_default_icon));
		buddyListItems.add(new BuddyListItem("kaush ",
				R.drawable.com_facebook_profile_default_icon));
		buddyListItems.add(new BuddyListItem("Saumya ",
				R.drawable.com_facebook_profile_default_icon));
		buddyListItems.add(new BuddyListItem("Kailash ",
				R.drawable.com_facebook_profile_default_icon));
		BuddyListAdapter adapter = new BuddyListAdapter(buddyListItems,
				getActivity());
		userMessageList.setAdapter(adapter);

		return rootView;
    }
}
