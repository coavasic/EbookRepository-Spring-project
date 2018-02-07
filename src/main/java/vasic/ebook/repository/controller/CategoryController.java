package vasic.ebook.repository.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vasic.ebook.repository.entity.Category;
import vasic.ebook.repository.service.CategoryService;

@RestController
public class CategoryController {
	
	
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping(value="open/categories/all",method=RequestMethod.GET)
	public ResponseEntity<List<Category>> getCategories(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getPrincipal());

		
		
		return new ResponseEntity<List<Category>>(categoryService.findAll(),HttpStatus.OK);
		
	}
	
	
	
	@RequestMapping(value="api/categories/add",method=RequestMethod.POST)
	public ResponseEntity<Category> add(@RequestBody Category category){
		
		return new ResponseEntity<Category>(this.categoryService.save(category),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@RequestMapping(value="api/categories/edit")
	public ResponseEntity<Category> update(@RequestBody Category category){
		
		Category oldCategory = categoryService.findOne(category.getId());
		if(oldCategory!=null) {
		oldCategory.setName(category.getName());
		
		return new ResponseEntity<Category>(this.categoryService.save(oldCategory),HttpStatus.OK);
		}else {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@RequestMapping(value="api/categories/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		
		if(categoryService.findOne(id)!=null) {
			
			categoryService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	
}
