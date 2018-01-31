package vasic.ebook.repository.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vasic.ebook.repository.dto.AppUserDTO;
import vasic.ebook.repository.entity.AppUser;
import vasic.ebook.repository.entity.Category;
import vasic.ebook.repository.repository.AppUserRepo;
import vasic.ebook.repository.repository.CategoryRepo;

@RestController
public class UserController {
	
	
	@Autowired
	private AppUserRepo userRepo;
	
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@RequestMapping(value="/sign-up",method=RequestMethod.POST)
	public ResponseEntity<AppUserDTO> signUp(@RequestBody AppUserDTO userDTO){
		
		
		
		AppUser user = new AppUser();
		
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setUsername(userDTO.getUsername());
		user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		user.setRole(userDTO.getRole());
		if(userDTO.getCategoryId()==0) {
			user.setCategory(null);
		}else {
			user.setCategory(categoryRepo.findOne(userDTO.getCategoryId()));
		}
		
		
		return new ResponseEntity<AppUserDTO>(new AppUserDTO(userRepo.save(user)),HttpStatus.OK);
		
		
		
		
		
	}
	
	
	@RequestMapping(value="my-category",method=RequestMethod.GET)
	public Integer getMyCatId() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getPrincipal().toString();
		AppUser appUser = userRepo.findByUsername(username);
		AppUserDTO appUserDTO = new AppUserDTO(appUser);
		
		return appUserDTO.getCategoryId();
		

	}
	
	@RequestMapping(value="my-role",method=RequestMethod.GET)
	public ResponseEntity<String> myRole() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getPrincipal().toString();
		AppUser appUser = userRepo.findByUsername(username);
		
		return new ResponseEntity<String>(appUser.getRole(),HttpStatus.OK);
		
	}
	

}
