package vasic.ebook.repository.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import vasic.ebook.repository.entity.EBook;

public class EBookDTO implements Serializable{
	
	
	private Integer id;
	private String title;
	private String author;
	private String keywords;
	private Integer publicationYear;
	private String fileName;
	private String mime;
	private Integer categoryId;
	private Integer languageId;
	
	public EBookDTO() {}

	public EBookDTO(Integer id, String title, String author, String keywords, Integer publicationYear, String fileName,
			String mime, Integer categoryId,Integer languageId) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.keywords = keywords;
		this.publicationYear = publicationYear;
		
		
		this.fileName = fileName;
		
		this.mime = mime;
		this.categoryId = categoryId;
		this.languageId=languageId;
	}
	
	public EBookDTO(EBook book) {
		
		this.id = book.getId();
		this.title = book.getTitle();
		this.author = book.getAuthor();
		this.keywords = book.getKeywords();
		this.publicationYear = book.getPublicationYear();
		this.fileName = book.getFileName();
		this.mime = book.getMime();
		this.categoryId = book.getCategory().getId();
		this.languageId=book.getLanguage().getId();
		
	}

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

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	
	
	
	
	
	

}
