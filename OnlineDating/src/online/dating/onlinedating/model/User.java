package online.dating.onlinedating.model;

import java.util.ArrayList;

public class User {
	String name;
	String email;
	String fbUserId;
	String gender;
	Double locationX, locationY;
	String userToken;
	String DeviceToken;
	String os;
	String age;
	ArrayList<String> imageList;

	public User(String name, String email, String fbUserId, String gender,
			Double locationX, Double locationY, String userToken,
			String deviceToken, String os, String age,
			ArrayList<String> imageList) {
		super();
		this.name = name;
		this.email = email;
		this.fbUserId = fbUserId;
		this.gender = gender;
		this.locationX = locationX;
		this.locationY = locationY;
		this.userToken = userToken;
		DeviceToken = deviceToken;
		this.os = os;
		this.age = age;
		this.imageList = imageList;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDeviceToken() {
		return DeviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		DeviceToken = deviceToken;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
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

	public ArrayList<String> getImageList() {
		return imageList;
	}

	public void setImageList(ArrayList<String> imageList) {
		this.imageList = imageList;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		return String.format(name + "," + email + "," + fbUserId + "," + gender
				+ "," + age + "," + userToken + "," + locationX + ","
				+ locationY + "," + DeviceToken + "," + os + "," + imageList);
	}

}
