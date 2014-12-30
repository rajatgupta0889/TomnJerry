package online.dating.onlinedating.model;

public class UserMessageItem {
	int imageIcon;
	String Message;
	Boolean isSelf;

	public UserMessageItem(int imageIcon, String message, Boolean isSelf) {
		super();
		this.imageIcon = imageIcon;
		Message = message;
		this.isSelf = isSelf;
	}

	public int getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(int imageIcon) {
		this.imageIcon = imageIcon;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public Boolean getIsSelf() {
		return isSelf;
	}

	public void setIsSelf(Boolean isSelf) {
		this.isSelf = isSelf;
	}

}
