package vasic.ebook.repository.entity;

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
public class EBook {
	
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
	
	@Size(max=100)
	@Column(name="mime")
	private String mime;
	
	@ManyToOne
	@JoinColumn(name="category_id",referencedColumnName="category_id",nullable=false)
	private Category category;

}
