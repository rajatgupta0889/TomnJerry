package online.daing.onlinedating;

import online.dating.onlinedating.Service.ImageLoader;
import online.dating.onlinedating.adapter.UserMessageAdapter;
import online.dating.onlinedating.model.User;
import online.dating.onlinedating.model.UserMessageItem;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class UserMessageActivity extends Activity {
	private static final String FIREBASE_URL = "https://onlinedating.firebaseio.com";
	private Firebase mFirebaseRef;
	private ValueEventListener mConnectedListener;
	private UserMessageAdapter mUserMessageAdapter;
	ImageView userImageView;
	String author;
	Firebase con;
	Firebase myConnectionsRef;
	// stores the timestamp of my last disconnect (the last time I was seen
	// online)
	Firebase lastOnlineRef;
	String id;
	String name;
	String icon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Firebase.setAndroidContext(getApplicationContext());
		setContentView(R.layout.user_chat_interface);
		Intent intent = getIntent();
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent)));
		Bundle messageData = intent.getExtras();
		if (messageData != null) {
			id = messageData.getString("id");
			name = messageData.getString(HomeFragment.intentNameTag);
			icon = messageData.getString("ImageIcon");
			System.out.println("Icon " + icon);
		}
		getActionBar().setTitle(name);
		mFirebaseRef = new Firebase(FIREBASE_URL).child(id);
		con = mFirebaseRef.push();
		if (User.tom != null) {
			author = User.tom.getName();
		} else {
			SharedPreferences pref = getSharedPreferences("pref", 0);
			String user = pref.getString(GetUserLogin.UserTom, null);
			if (user != null) {
				User.tom = User.getUser(user);
				author = User.tom.getName();
			}
		}
		userImageView = (ImageView) findViewById(R.id.coffeeImageView);
		ImageLoader imageLoader = new ImageLoader(this);
		imageLoader.DisplayImage(icon,
				R.drawable.com_facebook_profile_default_icon, userImageView);
		EditText inputText = (EditText) findViewById(R.id.locationSearchEditTextView);
		inputText
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView,
							int actionId, KeyEvent keyEvent) {
						if (actionId == EditorInfo.IME_NULL
								&& keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
							sendMessage();
						}
						return true;
					}
				});

		findViewById(R.id.sendButton).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						sendMessage();
					}
				});
	}

	@Override
	public void onStart() {
		super.onStart();
		// Setup our view and list adapter. Ensure it scrolls to the bottom as
		// data changes
		final ListView userMessageListView = (ListView) findViewById(R.id.chatMessageListView);
		// Tell our list adapter that we only want 50 messages at a time
		mUserMessageAdapter = new UserMessageAdapter(mFirebaseRef,
				R.layout.user_message_list_item_left,
				R.layout.user_message_list_item_right, this, author, this);
		userMessageListView.setAdapter(mUserMessageAdapter);
		userMessageListView.smoothScrollToPosition(userMessageListView
				.getCount() - 1);
		userMessageListView
				.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		userMessageListView.setStackFromBottom(true);
		mUserMessageAdapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				userMessageListView.setSelection(mUserMessageAdapter.getCount() - 1);
			}

		});

		// Finally, a little indication of connection status

		mConnectedListener = mFirebaseRef.getRoot().child(".info/connected")
				.addValueEventListener(new ValueEventListener() {

					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						boolean connected = (Boolean) dataSnapshot.getValue();
						System.out.println(dataSnapshot.getChildrenCount());
						if (connected) {
							Toast.makeText(getApplicationContext(),
									"Connected to Firebase", Toast.LENGTH_SHORT)
									.show();

						} else {
							Toast.makeText(getApplicationContext(),
									"Disconnected from Firebase",
									Toast.LENGTH_SHORT).show();

						}
					}

					@Override
					public void onCancelled(FirebaseError firebaseError) {
						// No-op
						Toast.makeText(getApplicationContext(), "Cancel",
								Toast.LENGTH_SHORT).show();
						System.out.println("FIre Base Error  " + firebaseError);
					}
				});
	}

	@Override
	public void onStop() {
		super.onStop();
		mFirebaseRef.getRoot().child(".info/connected")
				.removeEventListener(mConnectedListener);
		mUserMessageAdapter.cleanup();
	}

	private void sendMessage() {
		EditText inputText = (EditText) findViewById(R.id.locationSearchEditTextView);
		String input = inputText.getText().toString();
		if (!input.equals("")) {
			// Create our 'model', a Chat object
			UserMessageItem chat = new UserMessageItem(icon, input, true,
					author);
			// Create a new, auto-generated child of that chat location, and
			// save our chat data there
			mFirebaseRef.push().setValue(chat);
			inputText.setText("");
		}
	}

}
