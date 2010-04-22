package kth.projects.slutprojekt;

public class Profile {
	private String name;
	private KeyControls controls;
	
	public Profile(String name) {
		this.name = name;
		this.controls = new KeyControls();
	}
	
	
	public void setDefaultControls() {
		this.controls.setDefaultControls();
	}
	
	public void changeKeyControls(int left, int right, int forward, int fire) {
		this.controls.setRotateLeftButton(left);
		this.controls.setRotateRightButton(right);
		this.controls.setBoostForwardButton(forward);
		this.controls.setFireMainGunButton(fire);
	}
	
	public void saveProfile() {
		
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}

