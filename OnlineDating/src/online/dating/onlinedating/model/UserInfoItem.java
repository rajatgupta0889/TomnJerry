package online.dating.onlinedating.model;

public class UserInfoItem {
	private String user_info_text;
	private int user_info_image;

	public UserInfoItem(String user_info_text, int user_info_image) {
		super();
		this.user_info_text = user_info_text;
		this.user_info_image = user_info_image;
	}

	public String getUser_info_text() {
		return user_info_text;
	}

	public void setUser_info_text(String user_info_text) {
		this.user_info_text = user_info_text;
	}

	public int getUser_info_image() {
		return user_info_image;
	}

	public void setUser_info_image(int user_info_image) {
		this.user_info_image = user_info_image;
	}

}
