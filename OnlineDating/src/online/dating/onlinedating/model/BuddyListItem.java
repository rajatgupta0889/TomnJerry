package online.dating.onlinedating.model;

public class BuddyListItem {
	String userName;
	String userIcon;
	String buddyId;
	String buddyFbId;

	public BuddyListItem(String userName, String userIcon, String buddyId) {
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

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getBuddyFbId() {
		return buddyFbId;
	}

	public void setBuddyFbId(String buddyFbId) {
		this.buddyFbId = buddyFbId;
	}

}
