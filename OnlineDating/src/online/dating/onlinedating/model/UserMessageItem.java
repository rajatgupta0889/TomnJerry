package online.dating.onlinedating.model;

public class UserMessageItem {
	String imageIcon;
	String Message;
	Boolean isSelf;
	String author;

	public UserMessageItem() {
		// TODO Auto-generated constructor stub
	}

	public UserMessageItem(String imageIcon, String message, Boolean isSelf,
			String author) {
		super();
		this.imageIcon = imageIcon;
		Message = message;
		this.isSelf = isSelf;
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(String imageIcon) {
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
