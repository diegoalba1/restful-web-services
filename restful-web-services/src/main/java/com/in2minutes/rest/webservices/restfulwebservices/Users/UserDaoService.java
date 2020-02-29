package com.in2minutes.rest.webservices.restfulwebservices.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	private static List<User> users= new ArrayList<>();
	
	private static int usersCount =3;
	
	static {
		users.add(new User(1, "Adan", new Date()));
		users.add(new User(2, "Adan2", new Date()));
		users.add(new User(3, "Adan3", new Date()));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User saveUser(User theUser) {
		if (theUser.getId()==null) {
			theUser.setId(++usersCount);
		}
		users.add(theUser);
		return theUser;
	}
	
	public User findOne(int id) {
		for(User user : users) {
			if(user.getId()==id) {
				return user;
			}
		}
		return null;
	}
	
	public User deleteById(int id) {

		Iterator<User> iterator = users.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId()==id) {
				iterator.remove();
				return user;
			}
		}
		return null;
	}
	
}
