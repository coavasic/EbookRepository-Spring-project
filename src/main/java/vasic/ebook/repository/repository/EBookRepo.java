package vasic.ebook.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vasic.ebook.repository.entity.EBook;

public interface EBookRepo extends JpaRepository<Integer, EBook>{

}
