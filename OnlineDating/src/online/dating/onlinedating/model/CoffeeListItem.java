package online.dating.onlinedating.model;

public class CoffeeListItem {
	String coffeeUserName;
	int coffeeUserImage;

	public String getCoffeeUserName() {
		return coffeeUserName;
	}

	public void setCoffeeUserName(String coffeeUserName) {
		this.coffeeUserName = coffeeUserName;
	}

	public int getCoffeeUserImage() {
		return coffeeUserImage;
	}

	public void setCoffeeUserImage(int coffeeUserImage) {
		this.coffeeUserImage = coffeeUserImage;
	}

	public CoffeeListItem(String coffeeUserName, int coffeeUserImage) {
		super();
		this.coffeeUserName = coffeeUserName;
		this.coffeeUserImage = coffeeUserImage;
	}

}
