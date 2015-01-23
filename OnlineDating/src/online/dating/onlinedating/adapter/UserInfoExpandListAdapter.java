package online.dating.onlinedating.adapter;

import java.util.ArrayList;

import online.daing.onlinedating.R;
import online.dating.onlinedating.model.UserDetailItem;
import online.dating.onlinedating.model.UserInfoItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class UserInfoExpandListAdapter extends BaseExpandableListAdapter {
	private Context _context;
	private ArrayList<UserInfoItem> _userInfoItems; // header titles
	private UserDetailItem _userDetailItems;

	// child data in format of header title, child title
	public UserInfoExpandListAdapter(Context context,
			ArrayList<UserInfoItem> userInfoItems, UserDetailItem UserDetailItem) {
		this._context = context;
		this._userInfoItems = userInfoItems;
		this._userDetailItems = UserDetailItem;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return _userInfoItems.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return _userInfoItems.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return this._userDetailItems;

	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		UserInfoItem item = (UserInfoItem) getGroup(groupPosition);
		LayoutInflater infalInflater = (LayoutInflater) this._context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = infalInflater.inflate(R.layout.user_info_item, null);

		ImageView imageIcon = (ImageView) convertView
				.findViewById(R.id.user_info_image);
		TextView textItem = (TextView) convertView
				.findViewById(R.id.user_info_text);
//		Button selectButton = (Button) convertView.findViewById(R.id.button1);
//		selectButton.setText("Select");
		imageIcon.setImageResource(item.getUser_info_image());
		textItem.setText(item.getUser_info_text());

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		UserDetailItem item = (UserDetailItem) getChild(groupPosition,
				childPosition);
		LayoutInflater infalInflater = (LayoutInflater) this._context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (groupPosition == 0) {
			convertView = infalInflater.inflate(R.layout.user_height, null);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					RadioButton rb = (RadioButton) v;
					switch (rb.getId()) {
					case R.id.height_long:
						_userDetailItems.setHeight(2);
						break;
					case R.id.height_low:
						_userDetailItems.setHeight(0);
						break;
					default:
						_userDetailItems.setHeight(1);
						break;
					}
				}
			};

			RadioButton radioLow = (RadioButton) convertView
					.findViewById(R.id.height_low);
			radioLow.setOnClickListener(listener);
			RadioButton radioMed = (RadioButton) convertView
					.findViewById(R.id.height_med);
			radioMed.setOnClickListener(listener);
			RadioButton radioLong = (RadioButton) convertView
					.findViewById(R.id.height_long);
			radioLong.setOnClickListener(listener);
			RadioGroup radioGroup = (RadioGroup) convertView
					.findViewById(R.id.heightRadioGroup);
			radioGroup.clearCheck();

			if (item.getHeight() == 0) {
				radioLow.setChecked(true);
			}
			if (item.getHeight() == 1) {
				radioMed.setChecked(true);
			}
			if (item.getHeight() == 2) {
				radioLong.setChecked(true);
			}
		} else if (groupPosition == 1) {
			convertView = infalInflater.inflate(R.layout.user_passion, null);

		} else if (groupPosition == 2) {
			convertView = infalInflater.inflate(R.layout.user_sex, null);
			RadioButton radioMale = (RadioButton) convertView
					.findViewById(R.id.male);
			RadioButton radioFemale = (RadioButton) convertView
					.findViewById(R.id.female);
			if (item.getIsMale()) {
				radioMale.setChecked(true);
			} else {
				radioFemale.setChecked(true);
			}
		} else if (groupPosition == 3) {
			convertView = infalInflater.inflate(R.layout.user_profession, null);

		}

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
