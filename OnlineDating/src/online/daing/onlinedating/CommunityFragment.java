package online.daing.onlinedating;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import online.dating.onlinedating.Service.GetCoffeList;
import online.dating.onlinedating.Service.ImageLoader;
import online.dating.onlinedating.adapter.CoffeeListAdapter;
import online.dating.onlinedating.model.CoffeeListItem;
import online.dating.onlinedating.model.User;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CommunityFragment extends Fragment implements OnTaskCompleted,
		OnItemClickListener {

	public CommunityFragment() {
	}

	CoffeeListAdapter adapter;
	ArrayList<CoffeeListItem> userCoffeeItems;
	ListView userCoffeList;
	String fbUserId;
	String location;
	String dateTime;
	String coffeeId;
	ImageLoader imgLoader;
	private TextView coffeeTextView;
	private TextView fetchingTextView;
	private ProgressBar progBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.coffee_meet_list, container,
				false);
		if (User.tom == null) {
			SharedPreferences pref = getActivity().getSharedPreferences("pref",
					0);
			String user = pref.getString(GetUserLogin.UserTom, null);
			if (user != null) {
				User.tom = User.getUser(user);
			}
		}
		init(rootView);

		fbUserId = User.tom.getFbUserId();
		GetCoffeList coffeList = new GetCoffeList();
		coffeList.setListener(this);
		coffeList.execute();
		userCoffeList = (ListView) rootView
				.findViewById(R.id.coffeeUserListView);
		userCoffeeItems = new ArrayList<CoffeeListItem>();

		adapter = new CoffeeListAdapter(userCoffeeItems, getActivity());
		userCoffeList.setOnItemClickListener(this);

		return rootView;
	}

	private void init(View rootView) {
		coffeeTextView = (TextView) rootView.findViewById(R.id.buddyTextView);
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
		System.out.println("Inside Coffee Fragment");

		try {
			JSONObject coffeeObj = new JSONObject(result);
			String baseImageUrl = coffeeObj.getString("baseImgURL");
			JSONArray resultArray = coffeeObj.getJSONArray("coffees");
			for (int i = 0; i < resultArray.length(); i++) {
				JSONObject obj = resultArray.getJSONObject(i);
				CoffeeListItem item = new CoffeeListItem();
				location = obj.getString("location");
				dateTime = obj.getString("datetime");
				coffeeId = obj.getString("id");
				if (obj.getString("inviteeFbUserId").equalsIgnoreCase(fbUserId)) {
					item.setIsInvited(false);
					JSONObject invitedUser = obj.getJSONObject("invitedUser");
					item.setCoffeeUserName(invitedUser.getString("name"));
					JSONArray image = invitedUser.getJSONArray("images");
					String image_url = baseImageUrl + image.getString(0);
					item.setCoffeeUserImage(image_url);
				} else {
					item.setIsInvited(true);
					JSONObject inviteeobj = obj.getJSONObject("inviteeUser");
					item.setCoffeeUserName(inviteeobj.getString("name"));
					JSONArray image = inviteeobj.getJSONArray("images");
					String image_url = baseImageUrl + image.getString(0);
					item.setCoffeeUserImage(image_url);
				}
				userCoffeeItems.add(item);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		if (userCoffeeItems.size() < 1) {
			coffeeTextView.setVisibility(View.VISIBLE);
		}
		userCoffeList.setAdapter(adapter);
		progBar.setVisibility(View.INVISIBLE);
		fetchingTextView.setVisibility(View.INVISIBLE);
		Log.d("Frag", result);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		CoffeeListAdapter ca = (CoffeeListAdapter) parent.getAdapter();
		CoffeeListItem item = (CoffeeListItem) ca.getItem(position);
		Intent intent = null;
		if (item.getIsInvited()) {
			intent = new Intent(getActivity(),
					CoffeeMeetUpRequestActivity.class);
		} else {
			intent = new Intent(getActivity(), CoffeeMeetUpActivity.class);
		}
		intent.putExtra(HomeFragment.intentNameTag, item.getCoffeeUserName());
		intent.putExtra("ImageIcon", item.getCoffeeUserImage());
		intent.putExtra("location", location);
		intent.putExtra("datetime", dateTime);
		intent.putExtra("id", coffeeId);
		intent.putExtra("requestType", "coffeeList");
		startActivity(intent);
	}

	@Override
	public void onResult(String result, String resultType) {
		// TODO Auto-generated method stub

	}

}
