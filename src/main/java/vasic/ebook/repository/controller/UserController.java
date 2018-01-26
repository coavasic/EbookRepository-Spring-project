package vasic.ebook.repository.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vasic.ebook.repository.entity.AppUser;
import vasic.ebook.repository.repository.AppUserRepo;

@RestController
public class UserController {
	
	
	@Autowired
	private AppUserRepo userRepo;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@RequestMapping(value="/sign-up",method=RequestMethod.POST)
	public ResponseEntity<AppUser> signUp(@RequestBody AppUser user){
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		
		
		return new ResponseEntity<AppUser>(user,HttpStatus.OK);
		
		
		
		
	}
	

}
