package online.dating.onlinedating.adapter;

import java.util.ArrayList;

import online.daing.onlinedating.R;
import online.dating.onlinedating.Service.ImageLoader;
import online.dating.onlinedating.model.CoffeeListItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CoffeeListAdapter extends BaseAdapter {
	ArrayList<CoffeeListItem> coffeeListItems;
	Context context;

	public CoffeeListAdapter(ArrayList<CoffeeListItem> coffeeListItems,
			Context context) {
		super();
		this.coffeeListItems = coffeeListItems;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return coffeeListItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return coffeeListItems.get(position);
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
			rowView = mInflater.inflate(R.layout.user_coffee_item, null);
		}

		ImageView imgIcon = (ImageView) rowView
				.findViewById(R.id.userCoffeeIcon);

		TextView userChatName = (TextView) rowView
				.findViewById(R.id.userCoffeeName);

		userChatName.setText(coffeeListItems.get(position).getCoffeeUserName());
		ImageLoader imgLoader = new ImageLoader(context);
		imgLoader.DisplayImage(coffeeListItems.get(position)
				.getCoffeeUserImage(),
				R.drawable.abc_ab_stacked_solid_dark_holo, imgIcon);

		return rowView;
	}

}
