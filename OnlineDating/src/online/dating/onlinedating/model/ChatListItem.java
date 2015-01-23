package online.dating.onlinedating.model;

/*This class contain model for the chat list item 
 * The List havin two item
 * 1. User Icon 
 * 2. User Name 
 * */
public class ChatListItem {
	String chatUserName;
	String chatUserIcon;
	String buddyId;

	public String getChatUserName() {
		return chatUserName;
	}

	public void setChatUserName(String chatUserName) {
		this.chatUserName = chatUserName;
	}

	public String getChatUserIcon() {
		return chatUserIcon;
	}

	public void setChatUserIcon(String chatUserIcon) {
		this.chatUserIcon = chatUserIcon;
	}

	public ChatListItem(String chatUserName, String chatUserIcon, String buddyId) {
		super();
		this.chatUserName = chatUserName;
		this.chatUserIcon = chatUserIcon;
		this.buddyId = buddyId;
	}

	public String getBuddyId() {
		return buddyId;
	}

	public void setBuddyId(String buddyId) {
		this.buddyId = buddyId;
	}

}
