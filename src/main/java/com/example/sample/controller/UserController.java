package com.example.sample.controller;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sample.business.UserBusiness;
import com.example.sample.exception.ResourceNotFoundException;
import com.example.sample.model.User;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserBusiness userBusiness;
	
	private static final Logger log = Logger.getLogger(UserController.class);

	/**
	 * This method is going to create the user with given User object
	 * @param User
	 * @return User
	 * @author nkodumoori
	 */
	@PostMapping("/create")
	public ResponseEntity<String> createUser(@RequestBody User user) throws Exception {
		log.info("Creating user a new User with the userId : ");
		try{
			userBusiness.createUser(user);
		}catch(Exception exp){
			log.error("Getting exception while creating the User "+ exp.getMessage());
			throw new Exception("Unable to create an User");
		}
		log.info("User is created Successfully");
		return ResponseEntity.ok("User is created Successfully");
	}

	/**
	 * This method is going to update the user based on id
	 * @param id,userDetails
	 * @return User
	 * @author nkodumoori
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userDetails) throws ResourceNotFoundException, Exception {
		log.info("Updating user for the UserId : " + userId);
		User updatedUser = null;
		try{
			updatedUser = userBusiness.updateUser(userId, userDetails);
		}catch(ResourceNotFoundException notfoundExp){
			log.error("Getting ResourceNotFoundException while updating the User " + notfoundExp.getMessage());
			throw notfoundExp;
		}catch(Exception exp){
			log.error("Getting exception while updating the User" + exp.getMessage());
			throw exp;	
		}
		log.info("User is updated Successfully with the userId : " + userId);
		return ResponseEntity.ok(updatedUser);
	}

	/**
	 * This method is going to delete the user based on id
	 * @param id
	 * @return Boolean
	 * @author nkodumoori
	 */
	@DeleteMapping("/delete/{id}")
	public Boolean deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		log.info("Deleting user for the UserId : " + userId);
		try{
			userBusiness.deleteUser(userId);
		}catch(ResourceNotFoundException notfoundExp){
			log.error("Getting ResourceNotFoundException while deleting the User " + notfoundExp.getMessage());
			throw notfoundExp;
		}
		catch(Exception exp){
			log.error("Getting exception while deleting the User" + exp.getMessage());
			throw exp;	
		}
		log.info("User is deleted Successfully with the userId : " + userId);
		return Boolean.TRUE;
	}

	/**
	 * This method is going to get the list of all the user details
	 * @param
	 * @return List<User>
	 * @author nkodumoori
	 */
	@GetMapping("/users")
	public List<User> getAllUsers() throws Exception{
		log.info("Getting all the User details ");
		return userBusiness.getAllUsers();
	}

	/**
	 * This method is going to get the user details based on the id
	 * @param id
	 * @return User
	 * @author nkodumoori
	 */
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		log.info("Getting all the User details for userId : " + userId);
		User user = userBusiness.getUserById(userId);
		return ResponseEntity.ok().body(user);
	}

}