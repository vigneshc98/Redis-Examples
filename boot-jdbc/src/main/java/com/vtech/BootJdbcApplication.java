package com.vtech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vtech.dao.UserDao;

@SpringBootApplication
public class BootJdbcApplication implements Runnable {
	
	@Autowired
	private UserDao userDao;

	public static void main(String[] args) {
		SpringApplication.run(BootJdbcApplication.class, args);
	}

	@Override
	public void run() {
		System.out.println(userDao.createQuery());
	}

}
