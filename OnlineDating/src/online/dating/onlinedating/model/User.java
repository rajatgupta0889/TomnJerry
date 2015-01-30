package online.dating.onlinedating.model;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

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
	ArrayList<String> passion;
	String profession;
	String orientation;
	String height;
	public static User tom;

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
		passion = new ArrayList<String>();
		this.orientation = "straight";
		this.profession = "";
		this.height = "medium";

	}

	public User() {
		// TODO Auto-generated constructor stub

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

	public ArrayList<String> getPassion() {
		return passion;
	}

	public void setPassion(ArrayList<String> passion) {
		this.passion = passion;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		return String.format(name + ";" + email + ";" + fbUserId + ";" + gender
				+ ";" + age + ";" + userToken + ";" + locationX + ";"
				+ locationY + ";" + DeviceToken + ";" + os + ";" + imageList
				+ ";" + passion + ";" + profession + ";" + orientation + ";"
				+ height);
	}

	public static User getUser(String user) {
		User userObj = null;
		if (user != null) {
			userObj = new User();
			Log.d("User", user);
			String[] array = user.split(";");
			userObj.setName(array[0]);
			userObj.setEmail(array[1]);
			userObj.setFbUserId(array[2]);
			userObj.setGender(array[3]);
			userObj.setAge(array[4]);
			userObj.setUserToken(array[5]);
			userObj.setLocationX(Double.parseDouble(array[6]));
			userObj.setLocationY(Double.parseDouble(array[7]));
			userObj.setDeviceToken(array[8]);
			userObj.setOs(array[9]);
			if (array[10].contains(",")) {
				userObj.setImageList(new ArrayList<String>(Arrays
						.asList(array[10].split(","))));
			}
			if (array.length > 11) {
				if (array[11].contains(",")) {
					userObj.setPassion(new ArrayList<String>(Arrays
							.asList(array[10].split(","))));
				} else {
					userObj.setPassion(new ArrayList<String>());
				}
			}

			userObj.setProfession(array[12]);
			userObj.setOrientation(array[13]);
			userObj.setHeight(array[14]);

		}
		return userObj;

	}

	public static User getUser(JSONObject user) {
		User userObj = null;
		if (user != null) {
			userObj = new User();
			Log.d("User", " " + user);

			try {
				userObj.setName(user.getString("name"));
				userObj.setEmail(user.getString("name"));
				userObj.setFbUserId(user.getString("name"));
				userObj.setGender(user.getString("name"));
				userObj.setAge(user.getString("name"));
				userObj.setUserToken(user.getString("name"));
				userObj.setLocationX(Double.parseDouble(user.getString("name")));
				userObj.setLocationY(Double.parseDouble(user.getString("name")));
				userObj.setDeviceToken(user.getString("name"));
				userObj.setOs(user.getString("name"));
				JSONArray array = user.getJSONArray("images");
				ArrayList<String> temp = new ArrayList<String>();
				for (int i = 0; i < array.length(); i++) {
					temp.add(array.getString(i));
				}

				userObj.setImageList(temp);
				JSONArray array1 = user.getJSONArray("passion");
				if (array1.length() > 1) {
					ArrayList<String> temp1 = new ArrayList<String>();
					for (int i = 0; i < array.length(); i++) {
						temp1.add(array.getString(i));
					}
					userObj.setPassion(temp1);
					userObj.setProfession(user.getString("profession"));
					userObj.setOrientation(user.getString("orientation"));
					userObj.setHeight(user.getString("height"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return userObj;

	}
}
