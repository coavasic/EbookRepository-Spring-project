package vasic.ebook.repository.entity;

public class InfoChange {
	
	private String firstName;
	private String lastName;
	private String username;
	private Integer categoryId;
	
	public InfoChange() {}

	public InfoChange(String firstName, String lastName, String username, Integer categoryId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.categoryId = categoryId;
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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	
	
}
