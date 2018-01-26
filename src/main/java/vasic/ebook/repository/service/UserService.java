package vasic.ebook.repository.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import vasic.ebook.repository.entity.AppUser;

public interface UserService {
	
	AppUser findOne(Integer id);
	List<AppUser> findAll();
	AppUser save(AppUser user);
	void remove(Integer id);
	UserDetails loadUserByUsername(String username);

}
