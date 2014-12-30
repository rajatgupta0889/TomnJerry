package online.daing.onlinedating;

import java.util.ArrayList;

import online.dating.onlinedating.adapter.CoffeeListAdapter;
import online.dating.onlinedating.adapter.UserMessageAdapter;
import online.dating.onlinedating.model.CoffeeListItem;
import online.dating.onlinedating.model.UserMessageItem;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class UserMessageActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_chat_interface);
		ListView userMessageListView = (ListView) findViewById(R.id.chatMessageListView);
		ArrayList<UserMessageItem> userMessageItems = new ArrayList<UserMessageItem>();

		userMessageItems.add(new UserMessageItem(
				R.drawable.com_facebook_profile_default_icon, "Hi How are You??", true));
		userMessageItems.add(new UserMessageItem(
				R.drawable.com_facebook_profile_default_icon,"Hi",false));
		userMessageItems.add(new UserMessageItem(
				R.drawable.com_facebook_profile_default_icon,"I am Fine How are You?",false));
		userMessageItems.add(new UserMessageItem(
				R.drawable.com_facebook_profile_default_icon,"I am Also Fine ",true));
		userMessageItems.add(new UserMessageItem(
				R.drawable.com_facebook_profile_default_icon,"When you are coming Bangalore ",true));
		userMessageItems.add(new UserMessageItem(
				R.drawable.com_facebook_profile_default_icon,"I am coming this weekend ",false));
		UserMessageAdapter adapter = new UserMessageAdapter(userMessageItems,
				this);
		userMessageListView.setAdapter(adapter);
	}

	
}
