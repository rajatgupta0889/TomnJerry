package online.dating.onlinedating.adapter;

import java.util.ArrayList;

import online.daing.onlinedating.CoffeeMeetUpActivity;
import online.daing.onlinedating.CoffeeSetDateActivity;
import online.daing.onlinedating.R;
import online.daing.onlinedating.UserMessageActivity;
import online.dating.onlinedating.model.BuddyListItem;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BuddyListAdapter extends BaseAdapter {
	ArrayList<BuddyListItem> buddyListItems;
	Context context;

	public BuddyListAdapter(ArrayList<BuddyListItem> buddyListItems,
			Context context) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = null;
		if (rowView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			rowView = mInflater.inflate(R.layout.buddy_list_item, null);
		}
		ImageView chatImageView = (ImageView) rowView
				.findViewById(R.id.coffeeImageView);
		chatImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, UserMessageActivity.class);
				intent.putExtra("Name", buddyListItems.get(position)
						.getUserName());
				intent.putExtra("ImageIcon", buddyListItems.get(position)
						.getUserIcon());
				context.startActivity(intent);
			}
		});
		ImageView calImageView = (ImageView) rowView
				.findViewById(R.id.calendarImageView);
		calImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, CoffeeMeetUpActivity.class);
				intent.putExtra("Name", buddyListItems.get(position)
						.getUserName());
				intent.putExtra("ImageIcon", buddyListItems.get(position)
						.getUserIcon());
				context.startActivity(intent);
			}
		});
		ImageView imgIcon = (ImageView) rowView.findViewById(R.id.buddyImage);
		TextView buddyName = (TextView) rowView.findViewById(R.id.buddyName);

		buddyName.setText(buddyListItems.get(position).getUserName());
		imgIcon.setImageResource(buddyListItems.get(position).getUserIcon());

		return rowView;
	}

}
