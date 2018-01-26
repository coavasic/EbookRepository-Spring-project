package vasic.ebook.repository.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vasic.ebook.repository.entity.EBook;
import vasic.ebook.repository.repository.CategoryRepo;
import vasic.ebook.repository.repository.EBookRepo;


@Service
public class EBookServiceImpl implements EBookService{
	
	@Autowired
	EBookRepo eBookRepo;
	
	@Autowired
	CategoryRepo catRepo;
	
	@Override
	public EBook findOne(Integer id) {
		
		return eBookRepo.findOne(id);
	}

	@Override
	public List<EBook> findAll() {
		
		return eBookRepo.findAll();
	}

	@Override
	public EBook save(EBook book) {
		
		return eBookRepo.save(book);
	}

	@Override
	public void remove(Integer id) {
		
		eBookRepo.delete(id);
		
	}

	@Override
	public List<EBook> findAllByCategoryId(Integer id) {
		List<EBook> allEbooks = eBookRepo.findAll();
		List<EBook> filteredBooks  = new ArrayList<>();
		
		for (EBook eBook : allEbooks) {
			
			if(eBook.getCategory().getId()==id) {
				filteredBooks.add(eBook);
			}
			
		}
		
		return filteredBooks;
		
		
	}
	
	
	
	
	

}
