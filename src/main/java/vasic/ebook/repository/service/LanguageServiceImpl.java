package vasic.ebook.repository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vasic.ebook.repository.entity.Language;
import vasic.ebook.repository.repository.LanguageRepo;

@Service
public class LanguageServiceImpl implements LanguageService{
	
	@Autowired
	LanguageRepo languageRepo;

	@Override
	public Language findOne(Integer id) {
		
		return languageRepo.findOne(id);
	}

	@Override
	public List<Language> findAll() {
		
		return languageRepo.findAll();
	}

	@Override
	public Language save(Language language) {
		
		return languageRepo.save(language);
	}

	@Override
	public void remove(Integer id) {
		
		languageRepo.delete(id);
		
	}
	
	
	

}
