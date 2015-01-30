package online.dating.onlinedating.model;

public class NavDrawerItem {

	private int icon;
	private String count = "0";
	// boolean to set visiblity of the counter
	private boolean isCounterVisible = false;
	private int iconSelected;

	public NavDrawerItem() {
	}

	public NavDrawerItem(int icon, int iconSelected) {
		this.icon = icon;
		this.iconSelected = iconSelected;
	}

	public NavDrawerItem(int icon, boolean isCounterVisible, String count,
			int iconSelected) {

		this.icon = icon;
		this.isCounterVisible = isCounterVisible;
		this.count = count;
		this.iconSelected = iconSelected;
	}

	public int getIcon() {
		return this.icon;
	}

	public String getCount() {
		return this.count;
	}

	public boolean getCounterVisibility() {
		return this.isCounterVisible;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public void setCounterVisibility(boolean isCounterVisible) {
		this.isCounterVisible = isCounterVisible;
	}

	public boolean isCounterVisible() {
		return isCounterVisible;
	}

	public void setCounterVisible(boolean isCounterVisible) {
		this.isCounterVisible = isCounterVisible;
	}

	public int getIconSelected() {
		return iconSelected;
	}

	public void setIconSelected(int iconSelected) {
		this.iconSelected = iconSelected;
	}

}