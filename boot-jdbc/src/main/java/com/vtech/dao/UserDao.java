package com.vtech.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	private JdbcTemplate jdbcTemplate;
	
	public int createQuery() {
		String query = "CREATE TABLE IF NOT EXISTS User(id int primary key, name varchar(20),age int, city varchar(100) )";
		int update = this.jdbcTemplate.update(query);
		return update;
	}
	
	public int InsertUser(Integer id, String name, Integer age, String city) {
		String query = "insert into user(id,name,age,city) values(?,?,?,?)";
		int update = this.jdbcTemplate.update(query, new Object[]{id,name,age,city});
		return update;
	}
}
