package vasic.ebook.repository.service;

import java.util.List;

import vasic.ebook.repository.entity.EBook;

public interface EBookService {
	
	EBook findOne(Integer id);
	List<EBook> findAll();
	EBook save(EBook book);
	void remove(Integer id);
	List<EBook> findAllByCategoryId(Integer id);

}
