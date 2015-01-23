package online.dating.onlinedating.adapter;

import java.util.ArrayList;

import com.google.android.gms.internal.om;

import online.daing.onlinedating.R;
import online.dating.onlinedating.Service.ImageLoader;
import online.dating.onlinedating.model.ChatListItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatListAdapter extends BaseAdapter {
	ArrayList<ChatListItem> chatListItems;
	Context context;

	public ChatListAdapter(ArrayList<ChatListItem> chatListItems,
			Context context) {
		super();
		this.chatListItems = chatListItems;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return chatListItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return chatListItems.get(position);
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
		if (rowView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			rowView = mInflater.inflate(R.layout.chat_list_item, null);
		}

		ImageView imgIcon = (ImageView) rowView.findViewById(R.id.userChatIcon);

		TextView userChatName = (TextView) rowView
				.findViewById(R.id.userChatName);

		userChatName.setText(chatListItems.get(position).getChatUserName());
		ImageLoader imageLoader = new ImageLoader(context);
		imageLoader.DisplayImage(chatListItems.get(position).getChatUserName(),
				R.drawable.com_facebook_button_blue, imgIcon);
		return rowView;
	}

}
