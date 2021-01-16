package com.ace.springdemo.controller;

import java.util.List;

import org.apache.coyote.http11.filters.VoidInputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.context.Theme;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ace.springdemo.entity.Customer;
import com.ace.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	//need to inject the DAO to customer controller
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String showForm(Model theModel) {
		
		//get the customers from the dao
		List<Customer> customers = customerService.getCustomers();
		
		//add the customers to the model
		theModel.addAttribute("customers",customers);
		
		return "list-customer";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFromForAdd(Model theModel)
	{
		Customer customer = new Customer();
		
		theModel.addAttribute("addCustomer",customer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String SaveCustomer(@ModelAttribute("addCustomer") Customer theCustomer)
	{
		//save the Customer
		customerService.saveCustomer(theCustomer);
				
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int Id,Model theModel)
	{
		//get the customer from the update 
		Customer theCustomer = customerService.getCustomer(Id);
		
		//add the customer to the model
		theModel.addAttribute("addCustomer",theCustomer);
					
		return "customer-form";
	}
	
	@GetMapping("/showFormForDelete")
	public String updateCustomer(@RequestParam("customerId") int Id)
	{	
		
		//delete the customer 
		customerService.deleteCustomer(Id);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("theSearchName") String theSearchName,Model theModel)
	{	
		
		//delete the customer 
		List<Customer> theCustomer = customerService.searchCustomers(theSearchName);
		
		theModel.addAttribute("customers",theCustomer);
		
		return "list-customer";
	}
	
}
