package com.taner.todo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "sarlo1";
		String encodedPassword = encoder.encode(rawPassword);
		
		System.out.println(encodedPassword);
	}
}
