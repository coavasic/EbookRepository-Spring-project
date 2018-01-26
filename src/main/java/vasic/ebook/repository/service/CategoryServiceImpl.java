package vasic.ebook.repository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vasic.ebook.repository.entity.Category;
import vasic.ebook.repository.repository.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	CategoryRepo categoryRepo;
	

	@Override
	public Category findOne(Integer id) {
		
		return categoryRepo.findOne(id);
	}

	@Override
	public List<Category> findAll() {
		
		return categoryRepo.findAll();
	}

	@Override
	public Category save(Category category) {
		
		return categoryRepo.save(category);
	}

	@Override
	public void delete(Integer id) {
		
		categoryRepo.delete(id);
		
	}

}
