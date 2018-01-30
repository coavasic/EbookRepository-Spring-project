package vasic.ebook.repository.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vasic.ebook.repository.entity.Language;


import vasic.ebook.repository.service.LanguageService;

@RestController
public class LanguageController {
	
	@Autowired
	private LanguageService languageService;
	
	@RequestMapping(value="open/languages/all",method=RequestMethod.GET)
	public ResponseEntity<List<Language>>getAllLanguages(){
		
		List<Language> langs = new ArrayList<Language>();
		
		langs = languageService.findAll();
		
		
		
		return new ResponseEntity<List<Language>>(langs,HttpStatus.OK);
		
	}

}
