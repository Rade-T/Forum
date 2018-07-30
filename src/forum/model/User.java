package forum.model;

import java.util.Date;

public class User {
	private int id;
	private String userName, password, name, surname, email;
	private boolean disabled;
	private Date dateCreated;
	private String role;

	public User(int id, String userName, String password, String name, String surname, String email, boolean disabled,
			Date dateCreated, String role) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.disabled = disabled;
		this.dateCreated = dateCreated;
		this.role = role;
	}
	
	public User() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", name=" + name + ", surname="
				+ surname + ", email=" + email + ", disabled=" + disabled + ", dateCreated=" + dateCreated + ", role="
				+ role + "]";
	}
}
