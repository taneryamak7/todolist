package com.taner.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taner.todo.model.User;
import com.taner.todo.repository.UserRepository;

@Service
@Transactional
public class UserService {

 @Autowired
 private UserRepository repo;
  
 public List<User> listAll() {
     return (List<User>) repo.findAll();
 }
  
 public void save(User user) {
	 if (user.getId() == null) {
		 BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		 String bcPass = bc.encode(user.getPassword());
		 user.setPassword(bcPass);
	 }
     repo.save(user);
 }
  
 public User get(long id) {
     return repo.findById(id).get();
 }
  
 public void delete(long id) {
     repo.deleteById(id);
 }
}