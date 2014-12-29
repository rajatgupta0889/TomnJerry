package online.dating.onlinedating.adapter;

import java.util.ArrayList;
import java.util.List;

import online.daing.onlinedating.R;
import online.dating.onlinedating.model.BuddyListItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BuddyListAdapter extends BaseAdapter {
	ArrayList<BuddyListItem> buddyListItems;
	Context context;
	public BuddyListAdapter(ArrayList<BuddyListItem> buddyListItems, Context context) {
		super();
		this.buddyListItems = buddyListItems;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return buddyListItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return buddyListItems.get(position);
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
			rowView = mInflater.inflate(R.layout.buddy_list_item, null);
		}

		ImageView imgIcon = (ImageView) rowView
				.findViewById(R.id.buddyImage);
		TextView buddyName = (TextView) rowView
				.findViewById(R.id.buddyName);

		buddyName.setText(buddyListItems.get(position)
				.getUserName());
		imgIcon.setImageResource(buddyListItems.get(position).getUserIcon());

		return rowView;
	}

}
