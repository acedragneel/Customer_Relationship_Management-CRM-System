package com.ace.springdemo.dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ace.springdemo.entity.Customer;
import com.sun.xml.internal.messaging.saaj.util.TeeInputStream;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory SessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		
		//get the current hibernate session		
		Session session = SessionFactory.getCurrentSession();
		
		//create a query
		Query<Customer> theQuery = session.createQuery("from Customer order by lastName",Customer.class);
		
		//execute query and get the result list
		List<Customer> customers = theQuery.getResultList();
		
		//return the result	
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {

		//get the current hibernate session		
		Session session = SessionFactory.getCurrentSession();
		
		//save the customer
		session.saveOrUpdate(theCustomer);
		
	}

	@Override
	public Customer getCustomer(int id) {
		
		//get the current hibernate session		
		Session session = SessionFactory.getCurrentSession();
		
		Customer customer = session.get(Customer.class, id);
		
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		
		//get the current hibernate session		
		Session session = SessionFactory.getCurrentSession();
		
		//delete the object with the primary key
		Query theQuery = session.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", id);
				
		theQuery.executeUpdate();	
		/*//get the customer
		Customer customer = session.get(Customer.class, id);

		//delete the customer
		session.delete(customer);*/
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		
		//get the current hibernate session		
		Session session = SessionFactory.getCurrentSession();
		
		Query theQuery = null;

		if(theSearchName!=null
				&&theSearchName.trim().length()>0)
		{
			theQuery = session.createQuery("from Customer where lower(firstName) like:theName"
					+ " or lower(lastName) like:theName",Customer.class);
			theQuery.setParameter("theName", "%"+theSearchName.toLowerCase()+"%");
		}else
		{
			theQuery = session.createQuery("from Customer",Customer.class);
		}
		
		List<Customer> cutsomers =theQuery.getResultList(); 
		
		return cutsomers;
	}

}
