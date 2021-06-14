package com.example.SpringFirstApplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringFirstApplication.Service.customerService;
import com.example.SpringFirstApplication.dao.customerDao;
import com.example.SpringFirstApplication.dto.Customer;
import com.example.SpringFirstApplication.entity.User;
@CrossOrigin(origins="http://localhost:3000/")
@RestController
public class CustomerController {

	@Autowired
	public customerService customerService;
	

	
	@GetMapping("/getcustomer")
	public List<User> getCustomer()
	{
		
		List<User> cus = null;
		cus = customerService.getCustomer();
		System.out.println("Printing from controller"+cus);
		return cus;
	}
	@GetMapping("/resetAcc")
	public String unLockuser()
	{
		
		return "Your Account has been Unlocked Successfully ";
	}
}
