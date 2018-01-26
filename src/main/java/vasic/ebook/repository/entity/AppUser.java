package vasic.ebook.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

@Entity
@Table(name="users")
public class AppUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id", nullable=false, unique=true)
	private Integer id;
	
	@Size(max=30)
	@Column(name="firs_name")
	private String firstName;
	
	@Size(max=30)
	@Column(name="last_name")
	private String lastName;
	
	@Size(max=10)
	@Column(name="user_name", unique=true, nullable=false)
	private String username;

	@Column(name="password",nullable=false)
	private String password;
	
	@Size(max=30)
	@Column(name="role",nullable=false)
	private String role;
	
//	@ManyToOne
//	@JoinColumn(name="category_id",referencedColumnName="category_id",nullable=true)
//	private Category category;
	
	public AppUser() {}
	
	
	

	public AppUser(Integer id, String firstName, String lastName, String userName, String password, String type
			) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = userName;
		this.password = password;
		this.role = type;
//		this.category = category;
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

	public void setUsername(String userName) {
		this.username = userName;
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

	public void setRole(String type) {
		this.role = type;
	}

//	public Category getCategory() {
//		return category;
//	}
//
//	public void setCategory(Category category) {
//		this.category = category;
//	}
	
	
	
	
	

}
