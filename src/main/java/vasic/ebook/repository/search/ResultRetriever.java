package vasic.ebook.repository.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import vasic.ebook.repository.lucene.indexing.handlers.DocumentHandler;
import vasic.ebook.repository.lucene.indexing.handlers.PDFHandler;
import vasic.ebook.repository.lucene.model.IndexUnit;
import vasic.ebook.repository.lucene.model.RequiredHighlight;
import vasic.ebook.repository.lucene.model.ResultData;
import vasic.ebook.repository.repository.BookRepository;



@Service
public class ResultRetriever {
	
	@Autowired
	private BookRepository repository;
	
	public ResultRetriever(){
	}

	public List<ResultData> getResults(org.elasticsearch.index.query.QueryBuilder query,
			List<RequiredHighlight> requiredHighlights) {
		if (query == null) {
			return null;
		}
			
		List<ResultData> results = new ArrayList<ResultData>();
       
        for (IndexUnit indexUnit : repository.search(query)) {
        	results.add(new ResultData(indexUnit.getTitle(), indexUnit.getKeywords(), indexUnit.getFilename(), ""));
		}
        
		
		return results;
	}
	
	protected DocumentHandler getHandler(String fileName){
			if(fileName.endsWith(".pdf")){
			return new PDFHandler();
		}
			return null;
	}
}
