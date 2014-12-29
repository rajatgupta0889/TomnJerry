package online.daing.onlinedating;

import java.util.ArrayList;

import online.dating.onlinedating.adapter.CoffeeListAdapter;
import online.dating.onlinedating.model.CoffeeListItem;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class CommunityFragment extends Fragment {

	public CommunityFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.coffee_meet_list, container,
				false);

		final ListView userCoffeList = (ListView) rootView
				.findViewById(R.id.coffeeUserListView);
		ArrayList<CoffeeListItem> userCoffeeItems = new ArrayList<CoffeeListItem>();

		userCoffeeItems.add(new CoffeeListItem("Rajat ",
				R.drawable.com_facebook_profile_default_icon));
		userCoffeeItems.add(new CoffeeListItem("AMar ",
				R.drawable.com_facebook_profile_default_icon));
		userCoffeeItems.add(new CoffeeListItem("Parag ",
				R.drawable.com_facebook_profile_default_icon));
		userCoffeeItems.add(new CoffeeListItem("kaush ",
				R.drawable.com_facebook_profile_default_icon));
		userCoffeeItems.add(new CoffeeListItem("Saumya ",
				R.drawable.com_facebook_profile_default_icon));
		userCoffeeItems.add(new CoffeeListItem("Kailash ",
				R.drawable.com_facebook_profile_default_icon));
		CoffeeListAdapter adapter = new CoffeeListAdapter(userCoffeeItems,
				getActivity());
		userCoffeList.setAdapter(adapter);
		userCoffeList.setOnItemClickListener(new OnItemClickListener() {
			  
              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
                
               // ListView Clicked item index
               int itemPosition     = position;
               
               // ListView Clicked item value
                  CoffeeListAdapter ca = (CoffeeListAdapter) parent.getAdapter();
                  CoffeeListItem item = (CoffeeListItem) ca.getItem(position);
                  
                  
                // Show Alert 
                Toast.makeText(getActivity(),
                  "Position :"+itemPosition + "ListItem Name " + item.getCoffeeUserName(), Toast.LENGTH_SHORT)
                  .show();
             
              }
      });    

		return rootView;
	}

}
