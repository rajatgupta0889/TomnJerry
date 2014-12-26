package online.dating.onlinedating.model;

public class NavDrawerItem {

	private int icon;
	private String count = "0";
	// boolean to set visiblity of the counter
	private boolean isCounterVisible = false;

	public NavDrawerItem() {
	}

	public NavDrawerItem(int icon) {
		this.icon = icon;
	}

	public NavDrawerItem(int icon, boolean isCounterVisible, String count) {

		this.icon = icon;
		this.isCounterVisible = isCounterVisible;
		this.count = count;
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
}