package com.example.SpringFirstApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringFirstApplication.entity.User;
@Repository 
public interface customerDao extends JpaRepository<User,Integer> {
	
	@Override 
	public List<User> findAll();
	
}
