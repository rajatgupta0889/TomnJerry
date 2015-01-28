package online.dating.onlinedating.adapter;

import java.util.ArrayList;

import online.daing.onlinedating.CoffeeMeetUpRequestActivity;
import online.daing.onlinedating.R;
import online.dating.onlinedating.model.NotificationItem;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = null;
		if (rowView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			rowView = mInflater.inflate(R.layout.notification_list_item, null);
		}

		ImageView imgIcon = (ImageView) rowView.findViewById(R.id.userImage);
		ImageView coffeeIcon = (ImageView) rowView
				.findViewById(R.id.sideImageCoffee);
		ImageView userIcon = (ImageView) rowView
				.findViewById(R.id.sideImageUser);
		ImageView calIcon = (ImageView) rowView
				.findViewById(R.id.sideCalImageView);
		final TextView txtNotification = (TextView) rowView
				.findViewById(R.id.notificationText);
		txtNotification.setText(notificationItems.get(position)
				.getNotificationText());
		if (notificationItems.get(position).getIsCoffeeReq()) {
			userIcon.setVisibility(ImageView.INVISIBLE);
			rowView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intentExtra = ((Activity) context).getIntent();
					Bundle bundle = intentExtra.getExtras();
					if (bundle != null) {
						String CoffeeData = bundle.getString("match");
						Intent intent = new Intent(context,
								CoffeeMeetUpRequestActivity.class);
						intent.putExtra("coffeeData", CoffeeData);
						intent.putExtra("requestType", "notificationList");
						context.startActivity(intent);

					}

				}
			});
		} else {
			coffeeIcon.setVisibility(ImageView.INVISIBLE);
			calIcon.setVisibility(ImageView.INVISIBLE);
			rowView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Intent intent = new Intent(context,
					// MatchViewActivity.class);
					// intent.putExtra(HomeFragment.intentNameTag,
					// notificationItems.get(position)
					// .getNotificationText().toString()
					// .split(" ")[0]);
					// context.startActivity(intent);
				}
			});
		}

		return rowView;
	}
}
