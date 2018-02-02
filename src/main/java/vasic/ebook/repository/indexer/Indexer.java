package vasic.ebook.repository.indexer;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import vasic.ebook.repository.analysers.SerbianAnalyzer;
import vasic.ebook.repository.lucene.indexing.handlers.DocumentHandler;
import vasic.ebook.repository.lucene.indexing.handlers.PDFHandler;
import vasic.ebook.repository.lucene.model.IndexUnit;
import vasic.ebook.repository.repository.BookRepository;


@Service
public class Indexer {
	

	@Autowired
	private BookRepository repository;
	
	public Indexer() {
	}
	
	
	public boolean delete(String filename){
		if(repository.equals(filename)){
			repository.delete(filename);
			return true;
		} else
			return false;
		
	}
	
	public boolean update(IndexUnit unit){
		unit = repository.save(unit);
		if(unit!=null)
			return true;
		else
			return false;
	}
	
	public boolean add(IndexUnit unit){
		unit = repository.index(unit);
		if(unit!=null)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * @param file Direktorijum u kojem se nalaze dokumenti koje treba indeksirati
	 */
	public int index(File file){		
		DocumentHandler handler = null;
		String fileName = null;
		int retVal = 0;
		try {
			File[] files;
			if(file.isDirectory()){
				files = file.listFiles();
			}else{
				files = new File[1];
				files[0] = file;
			}
			for(File newFile : files){
				if(newFile.isFile()){
					fileName = newFile.getName();
					handler = getHandler(fileName);
					if(handler == null){
						System.out.println("Nije moguce indeksirati dokument sa nazivom: " + fileName);
						continue;
					}	
					if(add(handler.getIndexUnit(newFile)))
						retVal++;
				} else if (newFile.isDirectory()){
					retVal += index(newFile);
				}
			}
			System.out.println("indexing done");
		} catch (Exception e) {
			System.out.println("indexing NOT done");
		}
		return retVal;
	}
	

	public DocumentHandler getHandler(String fileName){
 if(fileName.endsWith(".pdf")){
			return new PDFHandler();
 		}
 	
 	return null;
 
	}

}