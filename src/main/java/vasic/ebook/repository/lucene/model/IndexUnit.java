package vasic.ebook.repository.lucene.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

public class IndexUnit {

	private String text;
	private String title;
	private	String keywords;
	private String filename;
	private String filedate;
	private String author;
	private String mime;
	
	
	
	
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
	
	
	
	public String getMime() {
		return mime;
	}
	public void setMime(String mime) {
		this.mime = mime;
	}
	public Document getLuceneDocument(){
		Document retVal = new Document();
		retVal.add(new TextField("text", text, Store.NO));
		retVal.add(new TextField("title", title, Store.YES));
		retVal.add(new TextField(keywords, keywords,Store.YES));
		retVal.add(new StringField("filename", filename, Store.YES));
		retVal.add(new TextField("filedate",filedate,Store.YES));
		return retVal;
	}

}
