package vasic.ebook.repository.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vasic.ebook.repository.entity.Category;
import vasic.ebook.repository.service.CategoryService;

@RestController
@RequestMapping(value="api/categories")
public class CategoryController {
	
	
	@Autowired
	CategoryService categoryService;

	@RequestMapping(value="/all",method=RequestMethod.GET)
	public ResponseEntity<List<Category>> getCategories(){
		
		
		return new ResponseEntity<List<Category>>(categoryService.findAll(),HttpStatus.OK);
		
	}
	

	
}
