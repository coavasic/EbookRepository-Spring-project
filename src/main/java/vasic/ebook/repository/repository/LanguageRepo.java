package vasic.ebook.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vasic.ebook.repository.entity.Language;

public interface LanguageRepo extends JpaRepository<Integer, Language>{

}
