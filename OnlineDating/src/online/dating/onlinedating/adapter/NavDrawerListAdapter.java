package online.dating.onlinedating.adapter;

import java.util.ArrayList;

import online.daing.onlinedating.R;
import online.dating.onlinedating.model.NavDrawerItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NavDrawerListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	ListView myList;

	public NavDrawerListAdapter(Context context,
			ArrayList<NavDrawerItem> navDrawerItems, ListView myList) {
		this.context = context;
		this.navDrawerItems = navDrawerItems;
		this.myList = myList;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = null;
		if (rowView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			rowView = mInflater.inflate(R.layout.drawer_list_item, null);
		}
		
		ImageView imgIcon = (ImageView) rowView.findViewById(R.id.icon);
		if(!myList.isItemChecked(position)){
		imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
		}else{
			imgIcon.setImageResource(navDrawerItems.get(position).getIconSelected());
		}

		return rowView;
	}

}