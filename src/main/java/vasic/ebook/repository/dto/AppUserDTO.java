package vasic.ebook.repository.dto;

import vasic.ebook.repository.entity.AppUser;

public class AppUserDTO {
	
	private Integer id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String role;
	private Integer categoryId;
	
	
	
	
	
	public AppUserDTO() {}
	
	
	public AppUserDTO(AppUser user) {
		
		this.id= user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.role = user.getRole();
		if(user.getCategory()==null) {
			this.categoryId=0;
		}else {
			this.categoryId=user.getCategory().getId();
		}
		
	}
	
	
	
	public Integer getId() {
		return id;
	}
	

	
	


	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	
	

}
