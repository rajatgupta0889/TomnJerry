package online.dating.onlinedating.model;

import java.util.ArrayList;

public class UserDetailItem {
	Boolean isMale;
	int height; /*
				 * if height = 0 value low , height= 1 value medium and height =
				 * 2 value long
				 */
	Boolean[] isPassionate;
	Boolean[] isProfession;
	ArrayList<String> passionArrayList;
	String UserProfession;

	public UserDetailItem(Boolean[] isProfession) {
		super();
		this.isProfession = isProfession;
	}

	public UserDetailItem(int height) {
		super();
		this.height = height;
	}

	public UserDetailItem(Boolean isMale) {
		super();
		this.isMale = isMale;
	}

	public ArrayList<String> getPassionArrayList() {
		return passionArrayList;
	}

	public void setPassionArrayList(ArrayList<String> passionArrayList) {
		this.passionArrayList = passionArrayList;
	}

	public String getUserProfession() {
		return UserProfession;
	}

	public void setUserProfession(String userProfession) {
		UserProfession = userProfession;
	}

	public UserDetailItem(Boolean isMale, int height, Boolean[] isPassionate,
			Boolean[] isProfession) {
		super();
		this.isMale = isMale;
		this.height = height;
		this.isPassionate = isPassionate;
		this.isProfession = isProfession;
		passionArrayList = new ArrayList<String>();
	}

	public Boolean getIsMale() {
		return isMale;
	}

	public void setIsMale(Boolean isMale) {
		this.isMale = isMale;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Boolean[] getIsPassionate() {
		return isPassionate;
	}

	public void setIsPassionate(Boolean[] isPassionate) {
		this.isPassionate = isPassionate;
	}

	public Boolean[] getIsProfession() {
		return isProfession;
	}

	public void setIsProfession(Boolean[] isProfession) {
		this.isProfession = isProfession;
	}

}
