package vasic.ebook.repository.controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vasic.ebook.repository.dto.AppUserDTO;
import vasic.ebook.repository.entity.AppUser;
import vasic.ebook.repository.entity.Category;
import vasic.ebook.repository.entity.InfoChange;
import vasic.ebook.repository.entity.PasswordChange;
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
		if(isUsernameUnique(userDTO.getUsername())) {
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setUsername(userDTO.getUsername());
			System.out.println(userDTO.getPassword());
			user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
			user.setRole("user");
			if(userDTO.getCategoryId()==0) {
				user.setCategory(null);
			}else {
				user.setCategory(categoryRepo.findOne(userDTO.getCategoryId()));
			}
			
			return new ResponseEntity<AppUserDTO>(new AppUserDTO(userRepo.save(user)),HttpStatus.OK);
			}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

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
	
	@RequestMapping(value="/api/users",method=RequestMethod.GET)
	public ResponseEntity<List<AppUserDTO>> getUsers(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String myUsername = auth.getPrincipal().toString();
		List<AppUserDTO> usersDTO = new ArrayList<AppUserDTO>();
		List<AppUser> users = userRepo.findAll();
		for(AppUser user:users) {
			if(!user.getUsername().equals(myUsername)) {
				usersDTO.add(new AppUserDTO(user));
			}
		}
		
		return new ResponseEntity<List<AppUserDTO>>(usersDTO,HttpStatus.OK);
			
		
		
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@RequestMapping(value="/api/promote/{username}",method=RequestMethod.GET)
	public ResponseEntity<?> promote(@PathVariable String username){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String myUsername = auth.getPrincipal().toString();
		if(!username.equals(myUsername)) {
			AppUser user = userRepo.findByUsername(username);
			user.setRole("admin");
			userRepo.save(user);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}			
	}
	
	@RequestMapping(value="open/username")
	public String getLoged() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getPrincipal());
		
		return auth.getPrincipal().toString();
		
	}
	
	@RequestMapping(value="/api/user/delete/{username}")
	public ResponseEntity<?> delete(@PathVariable String username){
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String myUsername = auth.getPrincipal().toString();
		if(!username.equals(myUsername)) {
			AppUser user = userRepo.findByUsername(username);
			userRepo.delete(user);
			return new ResponseEntity<>(HttpStatus.OK);			
		}else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		
	}
	
	@RequestMapping(value="/api/user/changePassword",method=RequestMethod.POST)
	public ResponseEntity<?> changeMyPassword(@RequestBody PasswordChange passwordChange){
		
		AppUser user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		if(passwordChange.getPassword().equals(passwordChange.getConfirmPassword())) {
			
			user.setPassword(bCryptPasswordEncoder.encode(passwordChange.getPassword()));
			userRepo.save(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	
	@PostMapping("/api/user/changeMyInfo")
	public ResponseEntity<?> changeMyInfo(@RequestBody InfoChange infoChange){
		
		AppUser user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		
		if(isUsernameUnique(infoChange.getUsername()) || infoChange.getUsername().equals(user.getUsername())) {
			
			user.setFirstName(infoChange.getFirstName());
			user.setLastName(infoChange.getLastName());
			user.setUsername(infoChange.getUsername());
			if(infoChange.getCategoryId()==0) {
				user.setCategory(null);
			}else {
				user.setCategory(categoryRepo.findOne(infoChange.getCategoryId()));
			}
			
			userRepo.save(user);
			
			return new ResponseEntity<>(HttpStatus.OK);
			
			
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		
	}
	
	@GetMapping("/api/user/myInfo")
	public ResponseEntity<AppUserDTO> getMyInfo(){
		
		AppUser user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		if(user != null) {
			AppUserDTO dto = new AppUserDTO(user);
			System.out.println("Id kategorije: "+dto.getCategoryId());
			return new ResponseEntity<AppUserDTO>(new AppUserDTO(user),HttpStatus.OK);
			
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	
	private boolean isUsernameUnique(String username){
		
		AppUser user = userRepo.findByUsername(username);
		if(user == null) {
			return true;
		}
		
		return false;
		
	}
	

}
