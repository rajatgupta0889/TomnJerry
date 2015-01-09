package online.daing.onlinedating;

import online.dating.onlinedating.adapter.UserMessageAdapter;
import online.dating.onlinedating.model.UserMessageItem;
import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.AuthResultHandler;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

public class UserMessageActivity extends Activity {
	private static final String FIREBASE_URL = "https://onlinedating.firebaseio.com";
	private Firebase mFirebaseRef;
	private ValueEventListener mConnectedListener;
	private UserMessageAdapter mUserMessageAdapter;
	String author;
	Firebase con;
	Firebase myConnectionsRef;
	// stores the timestamp of my last disconnect (the last time I was seen
	// online)
	Firebase lastOnlineRef;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Firebase.setAndroidContext(getApplicationContext());
		setContentView(R.layout.user_chat_interface);

		mFirebaseRef = new Firebase(FIREBASE_URL).child("Rajat");
		con = mFirebaseRef.push();
		TextView userName = (TextView) findViewById(R.id.chatMessageUserName);
		author = userName.getText().toString();

		myConnectionsRef = new Firebase(FIREBASE_URL + "users/" + author
				+ "/connections");
		lastOnlineRef = new Firebase(FIREBASE_URL + "/users/" + author
				+ "/lastOnline");
		mFirebaseRef.getRoot().authWithOAuthToken("facebook",
				Session.getActiveSession().getAccessToken(),
				new AuthResultHandler() {

					@Override
					public void onAuthenticationError(FirebaseError arg0) {
						// TODO Auto-generated method stub
						System.out.println("Sucees " + arg0);
					}

					@Override
					public void onAuthenticated(AuthData arg0) {
						// TODO Auto-generated method stub
						System.out.println("Error " + arg0);
					}
				});
		EditText inputText = (EditText) findViewById(R.id.userMessageEditText);
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

		findViewById(R.id.setSettingButton).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						sendMessage();
					}
				});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart() {
		super.onStart();
		// Setup our view and list adapter. Ensure it scrolls to the bottom as
		// data changes
		final ListView userMessageListView = (ListView) findViewById(R.id.chatMessageListView);
		// Tell our list adapter that we only want 50 messages at a time
		mUserMessageAdapter = new UserMessageAdapter(mFirebaseRef,
				R.layout.user_message_list_item_left, this, author);
		userMessageListView.setAdapter(mUserMessageAdapter);
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
		EditText inputText = (EditText) findViewById(R.id.userMessageEditText);
		String input = inputText.getText().toString();
		if (!input.equals("")) {
			// Create our 'model', a Chat object
			UserMessageItem chat = new UserMessageItem(
					R.drawable.com_facebook_profile_default_icon, input, true,
					author);
			// Create a new, auto-generated child of that chat location, and
			// save our chat data there
			mFirebaseRef.push().setValue(chat);
			inputText.setText("");
		}
	}

}
