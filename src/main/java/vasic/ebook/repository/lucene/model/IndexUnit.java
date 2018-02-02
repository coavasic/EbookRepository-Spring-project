package vasic.ebook.repository.lucene.model;

import java.util.ArrayList;

import java.util.List;

import org.apache.lucene.document.Field.Store;


import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

@Document(indexName = IndexUnit.INDEX_NAME, type = IndexUnit.TYPE_NAME, shards = 1, replicas = 0)
public class IndexUnit {
	

	public static final String INDEX_NAME = "digitallibrary";
	public static final String TYPE_NAME = "book";
	public static final String DATE_PATTERN = "yyyy";

	
	@Field(type=FieldType.String,index=FieldIndex.analyzed,store=true)
	private String text;
	@Field(type=FieldType.String,index=FieldIndex.analyzed,store=true)
	private String title;
	@Field(type=FieldType.String,index=FieldIndex.analyzed,store=true)
	private	String keywords;
	@Id
	@Field(type=FieldType.String,index=FieldIndex.not_analyzed,store=true)
	private String filename;
	@Field(type=FieldType.String,index=FieldIndex.analyzed,store=true)
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern=DATE_PATTERN)
	private String filedate;
	@Field(type=FieldType.String,index=FieldIndex.analyzed,store=true)
	private String author;
	
	
	
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFiledate() {
		return filedate;
	}
	public void setFiledate(String filedate) {
		this.filedate = filedate;
	}
	
	
	

//	public Document getLuceneDocument(){
//		Document retVal = new Document();
//		retVal.add(new TextField("text", text, Store.NO));
//		retVal.add(new TextField("title", title, Store.YES));
//		retVal.add(new TextField(keywords, keywords,Store.YES));
//		retVal.add(new StringField("filename", filename, Store.YES));
//		retVal.add(new TextField("filedate",filedate,Store.YES));
//		return retVal;
//	}

}
