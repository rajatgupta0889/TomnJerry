package online.dating.onlinedating.model;

public class UserDetailItem {
	Boolean isMale;
	int height; /*
				 * if height = 0 value low , height= 1 value medium and height =
				 * 2 value long
				 */
	Boolean[] isPassionate;
	Boolean[] isProfession;

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

	public UserDetailItem(Boolean isMale, int height, Boolean[] isPassionate,
			Boolean[] isProfession) {
		super();
		this.isMale = isMale;
		this.height = height;
		this.isPassionate = isPassionate;
		this.isProfession = isProfession;
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
