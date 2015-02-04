package online.dating.onlinedating.adapter;

import online.daing.onlinedating.R;
import online.dating.onlinedating.Service.ImageLoader;
import online.dating.onlinedating.model.User;
import online.dating.onlinedating.model.UserMessageItem;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;

public class UserMessageAdapter extends FirebaseListAdapter<UserMessageItem> {
	LayoutInflater mInflater;
	String mUserName;
	Context context;

	public UserMessageAdapter(Query mRef, int mLayout, int nLayout,
			Activity activity, String mUserName, Context context) {

		super(mRef, UserMessageItem.class, mLayout, nLayout, activity);
		this.mUserName = mUserName;
		mInflater = activity.getLayoutInflater();
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void populateView(View view, UserMessageItem model) {
		// TODO Auto-generated method stub
		String author = model.getAuthor();

		ImageView imgIcon = (ImageView) view
				.findViewById(R.id.userMessageImageView);
		TextView userMessage = (TextView) view
				.findViewById(R.id.userMessageTextView);

		userMessage.setText(model.getMessage());

		ImageLoader imageLoader = new ImageLoader(context);
		if (author.equalsIgnoreCase(User.tom.getName())) {
			SharedPreferences profilePref = context.getSharedPreferences(
					"profilePref", 0);

			String image_url = profilePref.getString("profilePic", "");
			imageLoader.DisplayImage(image_url,
					R.drawable.com_facebook_profile_default_icon, imgIcon);
		} else {
			imageLoader.DisplayImage(model.getImageIcon(),
					R.drawable.com_facebook_profile_default_icon, imgIcon);
			

		}

	}
}
