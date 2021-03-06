package online.dating.onlinedating.adapter;

import java.util.ArrayList;

import com.google.android.gms.internal.ma;

import online.daing.onlinedating.R;
import online.dating.onlinedating.model.UserDetailItem;
import online.dating.onlinedating.model.UserInfoItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class UserInfoExpandListAdapter extends BaseExpandableListAdapter {
	private Context _context;
	private ArrayList<UserInfoItem> _userInfoItems; // header titles
	private UserDetailItem _userDetailItems;
	CheckBox softwareCB;
	CheckBox graphicsCB;
	CheckBox managerCB;
	CheckBox accountantCB;
	CheckBox analystCb;
	CheckBox researcherCb;

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
		// Button selectButton = (Button)
		// convertView.findViewById(R.id.button1);
		// selectButton.setText("Select");
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
			CheckBox travelingCheckBox = (CheckBox) convertView
					.findViewById(R.id.travelingCB);
			CheckBox partyingCheckBox = (CheckBox) convertView
					.findViewById(R.id.partyingCB);
			CheckBox sportCheckBox = (CheckBox) convertView
					.findViewById(R.id.sportCB);
			CheckBox hikingCheckBox = (CheckBox) convertView
					.findViewById(R.id.hikingCB);
			CheckBox cyclingCheckBox = (CheckBox) convertView
					.findViewById(R.id.cyclingCB);
			CheckBox paintingCheckBox = (CheckBox) convertView
					.findViewById(R.id.paintingCB);
			setPadding(travelingCheckBox);
			setPadding(partyingCheckBox);
			setPadding(sportCheckBox);
			setPadding(hikingCheckBox);
			setPadding(cyclingCheckBox);
			setPadding(paintingCheckBox);

		} else if (groupPosition == 2) {
			convertView = infalInflater.inflate(R.layout.user_sex, null);
			convertView.setPadding(0, 0, 0, 0);
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
			softwareCB = (CheckBox) convertView.findViewById(R.id.SoftwareCB);
			graphicsCB = (CheckBox) convertView.findViewById(R.id.graphicsCB);
			managerCB = (CheckBox) convertView.findViewById(R.id.managerCB);
			accountantCB = (CheckBox) convertView
					.findViewById(R.id.accountantCB);
			analystCb = (CheckBox) convertView.findViewById(R.id.analystCB);
			researcherCb = (CheckBox) convertView.findViewById(R.id.researchCB);
			setPadding(softwareCB);
			setPadding(graphicsCB);
			setPadding(managerCB);
			setPadding(accountantCB);
			setPadding(analystCb);
			setPadding(researcherCb);

			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					init();
					CheckBox rb = (CheckBox) v;
					switch (rb.getId()) {
					case R.id.SoftwareCB:
						softwareCB.setChecked(true);
						break;
					case R.id.graphicsCB:
						graphicsCB.setChecked(true);
						break;
					case R.id.managerCB:
						managerCB.setChecked(true);
						break;
					case R.id.accountantCB:
						accountantCB.setChecked(true);
						break;
					case R.id.analystCB:
						analystCb.setChecked(true);
						break;
					case R.id.researchCB:
						researcherCb.setChecked(true);
						break;
					default:
						break;
					}

				}
			};

			softwareCB.setOnClickListener(listener);
			graphicsCB.setOnClickListener(listener);
			accountantCB.setOnClickListener(listener);
			analystCb.setOnClickListener(listener);
			researcherCb.setOnClickListener(listener);
			managerCB.setOnClickListener(listener);

		}

		return convertView;
	}

	public void init() {
		researcherCb.setChecked(false);
		graphicsCB.setChecked(false);
		managerCB.setChecked(false);
		softwareCB.setChecked(false);
		analystCb.setChecked(false);
		accountantCB.setChecked(false);
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	public void setPadding(CheckBox cb) {
		final float scale = _context.getResources().getDisplayMetrics().density;

		cb.setPadding(cb.getPaddingLeft() + (int) (10.0f * scale + 0.5f),
				cb.getPaddingTop(), cb.getPaddingRight(), cb.getPaddingBottom());
	}
}
