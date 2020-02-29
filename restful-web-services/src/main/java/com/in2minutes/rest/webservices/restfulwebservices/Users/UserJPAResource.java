package com.in2minutes.rest.webservices.restfulwebservices.Users;

import static  org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJPAResource {
	
	
	
	@Autowired
	private UserRepository userRespository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		userRespository.findAll().forEach(System.out::println);
		return userRespository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id){
		Optional<User> user =userRespository.findById(id);
		if(!user.isPresent()) {
			throw (new userNotFoundException("we couldnt find the user with id: "+id));
		}
		
		//"all users", SERVER_PATH + "/users"
		//retrieveAllUsers()
		//HATEOAS
		Resource<User> resource = new Resource<User>(user.get());
		
		
		ControllerLinkBuilder linkTo= linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));


		
		return resource;
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id){
		userRespository.deleteById(id);
		
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser=userRespository.save(user);
		
		//returns the current uri where the object is stored
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		
		//Return the response
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsUser(@PathVariable int id){
		Optional<User> userOptional = userRespository.findById(id);
		if(!userOptional.isPresent()) {
			throw (new userNotFoundException("we couldnt find the user with id: "+id));
		}
		
		return userOptional.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createUserPost(@PathVariable int id, @RequestBody Post post) {
		Optional<User> userOptional=userRespository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw (new userNotFoundException("we couldnt find the user with id: "+id));
		}
		
		
		User user = userOptional.get();
		post.setUser(user);
		
		postRepository.save(post);
		
		//returns the current uri where the object is stored
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
		
		//Return the response
		return ResponseEntity.created(location).build();
	}
	

}
