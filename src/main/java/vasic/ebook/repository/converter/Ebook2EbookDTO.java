package vasic.ebook.repository.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import vasic.ebook.repository.dto.EBookDTO;
import vasic.ebook.repository.entity.EBook;

@Component
public class Ebook2EbookDTO implements Converter<EBook, EBookDTO>{

	@Override
	public EBookDTO convert(EBook arg0) {
		
		EBookDTO bookDTO = new EBookDTO();
		bookDTO.setId(arg0.getId());
		bookDTO.setAuthor(arg0.getAuthor());
		bookDTO.setKeywords(arg0.getKeywords());
		bookDTO.setPublicationYear(arg0.getPublicationYear());
		bookDTO.setMime(arg0.getMime());
		
		if(arg0.getCategory()!=null) {
			
			bookDTO.setCategoryId(arg0.getCategory().getId());
		}
		
		
		return bookDTO;
		
		
	}
	
	
	public List<EBookDTO> convert(List<EBook> ebooks){
		
		
		List<EBookDTO> dtos = new ArrayList<EBookDTO>();
		
		for(EBook dto:ebooks) {
			
			dtos.add(convert(dto));
			
		}
		
		return dtos;
		
	}
	
	
	
	
	
	
	
}

