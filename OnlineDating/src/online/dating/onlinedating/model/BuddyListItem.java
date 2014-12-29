package online.dating.onlinedating.model;

public class BuddyListItem {
	String userName;
	int userIcon;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(int userIcon) {
		this.userIcon = userIcon;
	}

	public BuddyListItem(String userName, int userIcon) {
		super();
		this.userName = userName;
		this.userIcon = userIcon;
	}

}
