package vasic.ebook.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vasic.ebook.repository.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	User findByUserName(String username);

}
