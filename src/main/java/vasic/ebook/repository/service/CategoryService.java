package vasic.ebook.repository.service;

import java.util.List;

import vasic.ebook.repository.entity.Category;

public interface CategoryService {
	
	Category findOne(Integer id);
	List<Category> findAll();
	Category save(Category category);
	void delete(Integer id);

}
