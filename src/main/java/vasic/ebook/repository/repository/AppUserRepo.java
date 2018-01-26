package vasic.ebook.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vasic.ebook.repository.entity.AppUser;

public interface AppUserRepo extends JpaRepository<AppUser, Integer>{
	
	AppUser findByUsername(String username);

}
