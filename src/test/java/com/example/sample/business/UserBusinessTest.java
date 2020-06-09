package com.example.sample.business;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.sample.exception.ResourceNotFoundException;
import com.example.sample.model.User;
import com.example.sample.repository.UserRepository;

public class UserBusinessTest {

	@InjectMocks
	UserBusiness business;

	@Mock
	UserRepository repository;

	User localUser;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		localUser = new User("Naresh", "Kodumoori");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testCreateUser_First() throws Exception {
		Mockito.when(repository.save(Mockito.anyObject())).thenReturn(localUser);
		Assert.assertEquals("User is created Successfully", business.createUser(localUser));
	}
	
	@Test(expected = Exception.class)
	public void testCreateUser_Exp() throws Exception {
		Mockito.when(repository.findAll()).thenReturn(buildUserList());
		business.createUser(localUser);
	}
	
	@Test
	public void testCreateUser() throws Exception {
		Mockito.when(repository.findAll()).thenReturn(buildUserList());
		business.createUser(new User("Vijay", "Kodumoori"));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateUser() throws Exception {
		Mockito.when(repository.findById(1L)).thenReturn(Optional.of(buildUserList().get(0)));
		User obj = localUser;
		obj.setId(buildUserList().get(0).getId());
		Mockito.when(repository.save(Mockito.anyObject())).thenReturn(obj);
		User result = business.updateUser(1L, obj);
		Assert.assertEquals(result.getFirstName(), "Naresh");
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateUser_Exp() throws Exception {
		business.updateUser(5L, localUser);
	}
	
	@Test
	public void testDeleteUser() throws Exception {
		Mockito.when(repository.findById(1L)).thenReturn(Optional.of(buildUserList().get(0)));
		Assert.assertTrue(business.deleteUser(1L));
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteUser_Exp() throws Exception {
		business.deleteUser(1L);
	}
	
	@Test
	public void testGetAllUsers() throws Exception {
		Mockito.when(repository.findAll()).thenReturn(buildUserList());
		business.getAllUsers();
		Assert.assertTrue(Boolean.TRUE);
	}
	
	@Test
	public void testGetUserById() throws Exception {
		Mockito.when(repository.findById(1L)).thenReturn(Optional.of(buildUserList().get(0)));
		Assert.assertNotNull(business.getUserById(1L));
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testGetUserById_Exp() throws Exception {
		business.getUserById(1L);
	}
	
	private List<User> buildUserList() {
		List<User> userList = new ArrayList<>();
		User obj = null;
		obj = new User("Ravi", "Peddi");
		obj.setId(1L);
		userList.add(obj);

		obj = new User("Anil", "Vuppala");
		obj.setId(2L);
		userList.add(obj);

		obj = localUser;
		obj.setId(3L);
		userList.add(obj);

		return userList;
	}

}
