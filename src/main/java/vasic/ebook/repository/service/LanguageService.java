package vasic.ebook.repository.service;

import java.util.List;

import vasic.ebook.repository.entity.Language;

public interface LanguageService {
	
	
	Language findOne(Integer id);
	List<Language> findAll();
	Language save(Language language);
	void remove(Integer id);
	

}
