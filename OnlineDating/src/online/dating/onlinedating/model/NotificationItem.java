package online.dating.onlinedating.model;

public class NotificationItem {
	private String notificationText;
	private int userImage;
	private Boolean isCoffeeReq = false;
	private User user;

	public NotificationItem(int userImage, Boolean isCoffeeReq,
			String notificaitionText) {
		this.notificationText = notificaitionText;
		this.userImage = userImage;
		this.isCoffeeReq = isCoffeeReq;
	}

	public String getNotificationText() {
		return notificationText;
	}

	public int getUserImage() {
		return userImage;
	}

	public Boolean getIsCoffeeReq() {
		return isCoffeeReq;
	}

	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}

	public void setUserImage(int userImage) {
		this.userImage = userImage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setIsCoffeeReq(Boolean isCoffeeReq) {
		this.isCoffeeReq = isCoffeeReq;
	}

}
