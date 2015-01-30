package online.dating.onlinedating.adapter;

import online.daing.onlinedating.R;
import online.dating.onlinedating.model.UserMessageItem;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;

public class UserMessageAdapter extends FirebaseListAdapter<UserMessageItem> {
	LayoutInflater mInflater;
	String mUserName;

	public UserMessageAdapter(Query mRef, int mLayout, int nLayout,
			Activity activity, String mUserName) {

		super(mRef, UserMessageItem.class, mLayout, nLayout, activity);
		this.mUserName = mUserName;
		mInflater = activity.getLayoutInflater();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void populateView(View view, UserMessageItem model) {
		// TODO Auto-generated method stub
		String author = model.getAuthor();
		if (author != null && author.equals(mUserName)) {
		} else {
		}
		ImageView imgIcon = (ImageView) view
				.findViewById(R.id.userMessageImageView);
		TextView userMessage = (TextView) view
				.findViewById(R.id.userMessageTextView);
		userMessage.setText(model.getMessage());

		imgIcon.setImageResource(model.getImageIcon());

	}
}
