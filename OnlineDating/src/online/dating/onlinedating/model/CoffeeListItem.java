package online.dating.onlinedating.model;

public class CoffeeListItem {
	String coffeeUserName;
	String coffeeUserImage;
	Boolean isInvited;
	Boolean status;

	public String getCoffeeUserName() {
		return coffeeUserName;
	}

	public void setCoffeeUserName(String coffeeUserName) {
		this.coffeeUserName = coffeeUserName;
	}

	public String getCoffeeUserImage() {
		return coffeeUserImage;
	}

	public void setCoffeeUserImage(String coffeeUserImage) {
		this.coffeeUserImage = coffeeUserImage;

	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public CoffeeListItem(String coffeeUserName, String coffeeUserImage) {
		super();
		this.coffeeUserName = coffeeUserName;
		this.coffeeUserImage = coffeeUserImage;
		this.status = false;
	}

	public CoffeeListItem() {
		// TODO Auto-generated constructor stub
	}

	public Boolean getIsInvited() {
		return isInvited;
	}

	public void setIsInvited(Boolean isInvited) {
		this.isInvited = isInvited;
	}

}
