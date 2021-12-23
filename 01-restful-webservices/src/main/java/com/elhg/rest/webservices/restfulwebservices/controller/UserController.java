package com.elhg.rest.webservices.restfulwebservices.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.elhg.rest.webservices.restfulwebservices.dao.PostRepository;
import com.elhg.rest.webservices.restfulwebservices.dao.UserRepository;
import com.elhg.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.elhg.rest.webservices.restfulwebservices.model.Post;
import com.elhg.rest.webservices.restfulwebservices.model.User;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userService;

	@Autowired
	PostRepository postService;

	
	//retrieveAllUsers
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		return  userService.findAll();
	}
	
	//retrieveUser
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) throws Exception{
		Optional<User> user = userService.findById(Integer.valueOf(id));
			if(!user.isPresent())
				throw new UserNotFoundException("Id no encontrado : id-> "+id);
			
		EntityModel<User> model = EntityModel.of(user.get());
		
		//Link a otro metodo
		WebMvcLinkBuilder linkToUsers = 
				linkTo(methodOn(this.getClass()).retrieveAllUsers());
		model.add(linkToUsers.withRel("all-users"));

		return model;
		//return  userService.findOne(id);
	}
	
	//Create User
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User userSaved = userService.save(user);
		
		// Return => State : 201  Created
		// 			 Header Location : http://localhost:8080/users/6 
		URI location = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(userSaved.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}

	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) throws Exception{
		userService.deleteById(id);       
	}
	
	//retrieveAllPosts
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllPosts(@PathVariable int id){
		Optional<User> userOptional = userService.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id - "+id);
		}
		
		return userOptional.get().getPosts();
	}

	//Create Post
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		Optional<User> userOptional = userService.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id - "+id);
		}
		
		User user = userOptional.get();
		
		post.setUser(user);
		postService.save(post);
		
		// Return => State : 201  Created
		// 			 Header Location : http://localhost:8080/users/6 
		URI location = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}

}
