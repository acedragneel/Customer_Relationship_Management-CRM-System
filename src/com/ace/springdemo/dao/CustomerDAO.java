package com.ace.springdemo.dao;

import java.util.List;

import com.ace.springdemo.entity.Customer;

public interface CustomerDAO {

	List<Customer> getCustomers();

	void saveCustomer(Customer theCustomer);

	Customer getCustomer(int id);

	void deleteCustomer(int id);

	List<Customer> searchCustomers(String theSearchName);

}
