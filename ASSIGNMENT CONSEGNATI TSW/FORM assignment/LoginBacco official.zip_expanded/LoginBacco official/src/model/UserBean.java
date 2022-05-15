package model;

public class UserBean {
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	public boolean valid;

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isValid() {
		return this.valid;
	}

}
