package com.example.SpringFirstApplication.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringFirstApplication.Service.customerService;
import com.example.SpringFirstApplication.dao.customerDao;
import com.example.SpringFirstApplication.entity.User;

@Service
public class customerServiceImpl implements customerService {

	@Autowired
	public customerDao cusDao;
	
	@Override
	public List<User> getCustomer() {
		// TODO Auto-generated method stub
		//user.set
		return cusDao.findAll();
	}

}
