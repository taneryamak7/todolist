package com.taner.todo.service;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.taner.todo.model.User;

public class MyUserDetails implements UserDetails {
 
    private User user;
     
    public MyUserDetails(User user) {
        this.user = user;
    }
 
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
         
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
           
        return authorities;
    }

	public String getPassword() {
		
		return user.getPassword();
	}

	public String getUsername() {
		
		return user.getUsername();
	}

	public boolean isAccountNonExpired() {
		
		return true;
	}

	public boolean isAccountNonLocked() {
		
		return true;
	}

	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	public boolean isEnabled() {
	
		return true;
	}
 

}