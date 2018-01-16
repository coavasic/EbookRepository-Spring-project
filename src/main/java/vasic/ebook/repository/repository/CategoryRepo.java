package vasic.ebook.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vasic.ebook.repository.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
