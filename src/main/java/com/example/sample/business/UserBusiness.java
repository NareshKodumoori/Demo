package com.example.sample.business;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.sample.exception.ResourceNotFoundException;
import com.example.sample.model.User;
import com.example.sample.repository.UserRepository;

@Component
public class UserBusiness {
	
	private static final Logger log = Logger.getLogger(UserBusiness.class);
	
	@Autowired
	private UserRepository userRepository;
	
	public String createUser(User user) throws Exception {
		log.info("Creating user a new User ");
		try{
			List<User> userList = userRepository.findAll();
			
			for(User userObj : userList){
				if(userObj.getFirstName().equalsIgnoreCase(user.getFirstName()) && userObj.getLastName().equalsIgnoreCase(user.getLastName())){
					throw new Exception("Unable to create the User, as it is alreay in the DB with the combination of First/Last Name");
				}
			}
			Long userId; 
			Optional<Long> optional =   userList.stream().max(Comparator.comparing(User::getId)).map(e -> e.getId());
			if(optional.isPresent()){
				userId = optional.get()+ 1L;
			} else{
				userId = 1L;
			}
			user.setId(userId);
			userRepository.save(user);
		}catch(Exception exp){
			log.error("Getting exception while creating the User "+ exp.getMessage());
			throw exp;
		}
		log.info("User is created Successfully with the userId : " + user.getId());
		return "User is created Successfully";
	}

	public User updateUser(Long userId, User userDetails) throws ResourceNotFoundException, Exception {
		log.info("Updating user for the UserId : " + userId);
		
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		user.setLastName(userDetails.getLastName());
		user.setFirstName(userDetails.getFirstName());
		User updatedUser = userRepository.save(user);
		
		log.info("User is updated Successfully with the userId : " + userId);
		return updatedUser;
	}
	
	public Boolean deleteUser(Long userId) throws ResourceNotFoundException {
		log.info("Deleting user for the UserId : " + userId);
		
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		userRepository.delete(user);
		
		log.info("User is deleted Successfully with the userId : " + userId);
		return Boolean.TRUE;
	}
	
	public List<User> getAllUsers() throws Exception{
		log.info("Getting all the User details ");
		List<User> userList = userRepository.findAll();
		userList.stream().sorted(Comparator.comparing(User::getLastName));
		return userList;
	}
	
	public User getUserById(Long userId) throws ResourceNotFoundException {
		log.info("Getting all the User details for userId : " + userId);
		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
	}
}
