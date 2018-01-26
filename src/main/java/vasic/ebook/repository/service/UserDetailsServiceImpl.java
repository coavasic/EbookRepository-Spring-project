package vasic.ebook.repository.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vasic.ebook.repository.entity.AppUser;
import vasic.ebook.repository.repository.AppUserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private AppUserRepo userRepo;


	@Override
	public UserDetails loadUserByUsername(String username) {
		
		AppUser appUser = userRepo.findByUsername(username);
		if(appUser == null) {
			
			throw new UsernameNotFoundException(username);
		}
		
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(appUser.getRole()));
		
		return new User(appUser.getUsername(),appUser.getPassword(),authorities);
		
		
	}
	
	
	
	

}
