package online.dating.onlinedating.adapter;

import java.util.ArrayList;

import online.daing.onlinedating.R;
import online.dating.onlinedating.model.UserMessageItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserMessageAdapter extends BaseAdapter {
	ArrayList<UserMessageItem> userMessageItems;
	Context context;

	public UserMessageAdapter(ArrayList<UserMessageItem> userMessageItems,
			Context context) {
		super();
		this.userMessageItems = userMessageItems;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return userMessageItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return userMessageItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = null;
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		UserMessageItem item = userMessageItems.get(position);

		if (item.getIsSelf()) {
			rowView = mInflater.inflate(R.layout.user_message_list_item_right,
					null);

		} else {
			rowView = mInflater.inflate(R.layout.user_message_list_item_left,
					null);
		}

		ImageView imgIcon = (ImageView) rowView
				.findViewById(R.id.userMessageImageView);

		TextView userMessage = (TextView) rowView
				.findViewById(R.id.userMessageTextView);

		userMessage.setText(item.getMessage());
		imgIcon.setImageResource(item.getImageIcon());

		return rowView;
	}

}
