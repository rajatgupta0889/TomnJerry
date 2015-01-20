package online.dating.onlinedating.model;

public class BuddyListItem {
	String userName;
	int userIcon;
	String buddyId;

	public BuddyListItem(String userName, int userIcon, String buddyId) {
		super();
		this.userName = userName;
		this.userIcon = userIcon;
		this.buddyId = buddyId;
	}

	public String getBuddyId() {
		return buddyId;
	}

	public void setBuddyId(String buddyId) {
		this.buddyId = buddyId;
	}

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

}
