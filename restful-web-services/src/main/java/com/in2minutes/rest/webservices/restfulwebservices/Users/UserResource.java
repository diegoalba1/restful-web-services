package com.in2minutes.rest.webservices.restfulwebservices.Users;

import static  org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;

import java.util.List;

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
public class UserResource {
	
	@Autowired
	private UserDaoService service2;
	
	
	@GetMapping("users")
	public List<User> retrieveAllUsers(){
		service2.findAll().forEach(System.out::println);
		return service2.findAll();
	}
	
	@GetMapping("users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id){
		User user =service2.findOne(id);
		if(user==null) {
			throw (new userNotFoundException("we couldnt find the user with id: "+id));
		}
		
		//"all users", SERVER_PATH + "/users"
		//retrieveAllUsers()
		//HATEOAS
		Resource<User> resource = new Resource<User>(user);
		
		
		ControllerLinkBuilder linkTo= linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));


		
		return resource;
	}
	
	@DeleteMapping("users/{id}")
	public void deleteUser(@PathVariable int id){
		User user =service2.deleteById(id);
		if(user==null) {
			throw (new userNotFoundException("we couldnt find the user with id: "+id));
		}
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser=service2.saveUser(user);
		
		//returns the current uri where the object is stored
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		
		//Return the response
		return ResponseEntity.created(location).build();
	}

}
