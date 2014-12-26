package online.dating.onlinedating.adapter;

import java.util.ArrayList;

import online.daing.onlinedating.R;
import online.dating.onlinedating.model.NavDrawerItem;
import online.dating.onlinedating.model.NotificationItem;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<NotificationItem> notificationItems;

	public NotificationListAdapter(Context context,
			ArrayList<NotificationItem> notificationItems) {
		this.context = context;
		this.notificationItems = notificationItems;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return notificationItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return notificationItems.get(position);
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
			rowView = mInflater.inflate(R.layout.notification_list_item, null);
		}

		ImageView imgIcon = (ImageView) rowView
				.findViewById(R.id.userImage);
		ImageView coffeeIcon = (ImageView) rowView
				.findViewById(R.id.sideImageCoffee);
		ImageView userIcon = (ImageView) rowView
				.findViewById(R.id.sideImageUser);
		ImageView calIcon = (ImageView) rowView
				.findViewById(R.id.sideCalImageView);
		TextView txtNotification = (TextView) rowView
				.findViewById(R.id.notificationText);

		txtNotification.setText(notificationItems.get(position)
				.getNotificationText());
		if (notificationItems.get(position).getIsCoffeeReq()) {
			userIcon.setVisibility(ImageView.INVISIBLE);
		} else {
			coffeeIcon.setVisibility(ImageView.INVISIBLE);
			calIcon.setVisibility(ImageView.INVISIBLE);
		}

		return rowView;
	}
}
