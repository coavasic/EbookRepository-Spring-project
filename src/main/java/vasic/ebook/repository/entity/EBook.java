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

@Entity
@Table(name="ebooks")
public class EBook implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ebook_id",nullable=false, unique=true)
	private Integer id;
	
	
	@Size(max=30)
	@Column(name="title")
	private String title;
	
	@Size(max=120)
	@Column(name="author")
	private String author;
	
	
	@Size(max=120)
	@Column(name="keywords")
	private String keywords;
	
	@Column(name="publication_year")
	private Integer publicationYear;
	
	@Size(max=200)
	@Column(name="file_name")
	private String fileName;
	
	
	@ManyToOne
	@JoinColumn(name="category_id",referencedColumnName="category_id",nullable=false)
	private Category category;
	
	@ManyToOne
	@JoinColumn(name="langueage_id",referencedColumnName="language_id",nullable=false)
	private Language language;
	
	
	public EBook() {}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getKeywords() {
		return keywords;
	}


	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}


	public Integer getPublicationYear() {
		return publicationYear;
	}


	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public Language getLanguage() {
		return language;
	}


	public void setLanguage(Language language) {
		this.language = language;
	}
	
	
	
	

}
