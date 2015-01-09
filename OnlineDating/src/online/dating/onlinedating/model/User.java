package online.dating.onlinedating.model;

public class User {
	String name;
	String email;
	String fbUserId;
	String gender;
	Double locationX, locationY;
	String userToken;

	public User(String name, String email, String fbUserId, String gender,
			Double locationX, Double locationY, String userToken) {
		super();
		this.name = name;
		this.email = email;
		this.fbUserId = fbUserId;
		this.gender = gender;
		this.locationX = locationX;
		this.locationY = locationY;
		this.userToken = userToken;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFbUserId() {
		return fbUserId;
	}

	public void setFbUserId(String fbUserId) {
		this.fbUserId = fbUserId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Double getLocationX() {
		return locationX;
	}

	public void setLocationX(Double locationX) {
		this.locationX = locationX;
	}

	public Double getLocationY() {
		return locationY;
	}

	public void setLocationY(Double locationY) {
		this.locationY = locationY;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		return String.format(name + "," + email + "," + fbUserId + "," + gender
				+ "," + userToken + "," + locationX + "," + locationY);
	}

}
