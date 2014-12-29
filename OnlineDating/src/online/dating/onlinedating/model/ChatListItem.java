package online.dating.onlinedating.model;

/*This class contain model for the chat list item 
 * The List havin two item
 * 1. User Icon 
 * 2. User Name 
 * */
public class ChatListItem {
	String chatUserName;
	int chatUserIcon;

	public String getChatUserName() {
		return chatUserName;
	}

	public void setChatUserName(String chatUserName) {
		this.chatUserName = chatUserName;
	}

	public int getChatUserIcon() {
		return chatUserIcon;
	}

	public void setChatUserIcon(int chatUserIcon) {
		this.chatUserIcon = chatUserIcon;
	}

	public ChatListItem(String chatUserName, int chatUserIcon) {
		super();
		this.chatUserName = chatUserName;
		this.chatUserIcon = chatUserIcon;
	}

}
