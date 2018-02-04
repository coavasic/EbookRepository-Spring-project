package vasic.ebook.repository.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vasic.ebook.repository.dto.EBookDTO;
import vasic.ebook.repository.entity.EBook;
import vasic.ebook.repository.lucene.indexing.handlers.DocumentHandler;
import vasic.ebook.repository.lucene.indexing.handlers.PDFHandler;
import vasic.ebook.repository.lucene.model.IndexUnit;
import vasic.ebook.repository.lucene.model.RequiredHighlight;
import vasic.ebook.repository.repository.BookRepository;
import vasic.ebook.repository.repository.EBookRepo;



@Service
public class ResultRetriever {
	
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private EBookRepo ebookRepo;
	
	public ResultRetriever(){
	}

	public List<EBookDTO> getResults(org.elasticsearch.index.query.QueryBuilder query,
			List<RequiredHighlight> requiredHighlights) {
		if (query == null) {
			return null;
		}
       
//        for (IndexUnit indexUnit : repository.search(query)) {
//        	results.add(new ResultData(indexUnit.getTitle(), indexUnit.getKeywords(), indexUnit.getFilename(), ""));
//		}
        
		
		return getEbooksByIndexUnit(repository.search(query));
	}
	
	protected DocumentHandler getHandler(String fileName){
			if(fileName.endsWith(".pdf")){
			return new PDFHandler();
		}
			return null;
	}
	
	private List<EBookDTO> getEbooksByIndexUnit(Iterable<IndexUnit> iterable){
		
		List<String> filenames = new ArrayList<>();
		for(IndexUnit data: iterable) {
			if(!filenames.contains(data.getFilename())) {
				filenames.add(data.getFilename());
			}
		}			
		
		List<EBookDTO> dtos = new ArrayList<>();
		for(String fullFileName: filenames) {
			String[] strings = fullFileName.split("\\\\");
			String fileName = strings[strings.length - 1];
			EBook ebook = ebookRepo.findByFileName(fileName);
			if(ebook!=null) {
			dtos.add(new EBookDTO(ebook));
			}
		}
		
		return dtos;
		
	}
}
